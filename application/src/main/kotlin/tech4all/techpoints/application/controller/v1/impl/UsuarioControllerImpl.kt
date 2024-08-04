package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.UsuarioController
import tech4all.techpoints.domain.dto.UsuarioDTOInput
import tech4all.techpoints.domain.dto.UsuarioDTOOutput
import tech4all.techpoints.domain.exception.*
import tech4all.techpoints.domain.service.UsuarioService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/v1/usuario")
@Tag(name = "Usuario", description = "Endpoints para gerenciamento de usuários")
class UsuarioControllerImpl(
    private val usuarioService: UsuarioService
) : UsuarioController {

    @PostMapping("/cadastro")
    override fun post(@RequestBody @Valid usuarioDTOInput: UsuarioDTOInput): ResponseEntity<UsuarioDTOOutput> {
        val usuario = usuarioService.cadastrarUsuario(usuarioDTOInput)
        return ResponseEntity.status(201).body(usuario)
    }

    @PostMapping("/deletar")
    override fun softDelete(@RequestBody @Valid requestBody: Map<String, String>): ResponseEntity<Map<String, String>> {
        val idUsuario = requestBody["idUsuario"]?.toIntOrNull() ?: throw InvalidRequestDataException("Dados de solicitação inválidos")
        usuarioService.softDeleteUsuario(idUsuario)
        return ResponseEntity.ok(mapOf("message" to "Usuário deletado com sucesso"))
    }

    @DeleteMapping("/deletar")
    @Transactional
    override fun hardDelete(@RequestBody @Valid requestBody: Map<String, String>): ResponseEntity<Map<String, String>> {
        val idUsuario = requestBody["idUsuario"]?.toIntOrNull() ?: throw InvalidRequestDataException("Dados de solicitação inválidos")
        usuarioService.hardDeleteUsuario(idUsuario)
        return ResponseEntity.ok(mapOf("message" to "Usuário deletado com sucesso"))
    }

    @GetMapping("/listar")
    override fun listar(): ResponseEntity<List<UsuarioDTOOutput>> {
        val usuarios = usuarioService.findAll()
        return if (usuarios.isNotEmpty()) {
            ResponseEntity.ok(usuarios)
        } else {
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/buscar/{idUsuario}")
    override fun buscar(@PathVariable idUsuario: Int): ResponseEntity<UsuarioDTOOutput> {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorId(idUsuario))
    }

    @PostMapping("/login")
    override fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<UsuarioDTOOutput> {
        val email = loginData["email"] ?: throw InvalidRequestDataException("Dados de login inválidos")
        val senha = loginData["senha"] ?: throw InvalidRequestDataException("Dados de login inválidos")
        val usuario = usuarioService.login(email, senha)
        return ResponseEntity.ok(usuario)
    }

    @PostMapping("/logoff")
    override fun logoff(@RequestParam idUsuario: Int): ResponseEntity<Map<String, String>> {
        usuarioService.logoff(idUsuario)
        return ResponseEntity.ok(mapOf("message" to "Usuário desautenticado com sucesso"))
    }

    @GetMapping("/buscarPorEmail")
    override fun buscarPorEmail(@RequestParam email: String): ResponseEntity<UsuarioDTOOutput> {
        val usuario = usuarioService.findByEmail(email)
        return usuario.map { ResponseEntity.ok(it) }
            .orElseThrow { UsuarioNotFoundException("Usuário não encontrado") }
    }

    @PatchMapping("/atualizar/{idUsuario}")
    override fun patchUsuario(
        @PathVariable idUsuario: Int,
        @RequestBody atualizacao: Map<String, Any>
    ): ResponseEntity<UsuarioDTOOutput> {
        val usuarioAtualizado = usuarioService.atualizarUsuario(idUsuario, atualizacao)
        return ResponseEntity.ok(usuarioAtualizado)
    }

    @PatchMapping("/atualizar-imagem/{idUsuario}", consumes = ["image/jpeg", "image/png", "image/jpg"])
    override fun patchImagem(@PathVariable idUsuario: Int, @RequestBody novaFoto: ByteArray): ResponseEntity<Map<String, String>> {
        usuarioService.atualizarImagemPerfil(idUsuario, novaFoto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/imagem/{idUsuario}", produces = ["image/*"])
    override fun getImagemPerfil(@PathVariable idUsuario: Int): ResponseEntity<ByteArrayResource> {
        val imagem = usuarioService.getImagemPerfil(idUsuario)
        return ResponseEntity.ok(ByteArrayResource(imagem))
    }
}
