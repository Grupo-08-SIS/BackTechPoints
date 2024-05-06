package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dominio.UsuarioDTO
import TechForAll.techPoints.repository.EnderecoRepository
import TechForAll.techPoints.repository.UsuarioRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/usuarios")
class UsuarioController {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    lateinit var enderecoRepository: EnderecoRepository

    @Operation(summary = "Cadastrar um novo usuário", description = "Retorna os detalhes do usuário cadastrado")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            ApiResponse(responseCode = "400", description = "Email já cadastrado")
        ]
    )
    @PostMapping("/cadastro")
    fun post(@RequestBody @Valid novoUsuario: Usuario): ResponseEntity<Any> {
        val usuarioExistente = usuarioRepository.existsByEmail(novoUsuario.email)

        if (usuarioExistente) {
            return ResponseEntity.status(400).build()
        }
        val usuarioSalvo = usuarioRepository.save(novoUsuario)
        val novoUsuarioDTO = usuarioParaDTO(usuarioSalvo)
        return ResponseEntity.status(201).body(novoUsuarioDTO)
    }

    @Operation(summary = "Soft delete de um usuário", description = "Marca o usuário como deletado, sem remover do banco")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            ApiResponse(responseCode = "400", description = "Credenciais inválidas")
        ]
    )
    @PostMapping("/deletar")
    fun softDelete(@RequestBody @Valid usuario: Usuario): ResponseEntity<Any> {
        if (usuarioRepository.findByEmailAndSenha(usuario.email, usuario.senha) == null) {
            return ResponseEntity.status(400).build()
        }
        usuario.deletado = true
        usuario.dataDeletado = LocalDateTime.now()
        usuario.dataAtualizacao = LocalDateTime.now()
        usuarioRepository.save(usuario)
        return ResponseEntity.status(200).build()
    }

    @Operation(summary = "Hard delete de um usuário", description = "Remove o usuário do banco de dados permanentemente")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            ApiResponse(responseCode = "400", description = "Credenciais inválidas"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping("/deletar")
    fun hardDelete(@RequestBody @Valid usuario: Usuario): ResponseEntity<Any> {
        if (usuarioRepository.findByEmailAndSenha(usuario.email, usuario.senha) == null) {
            return ResponseEntity.status(400).build()
        }
        enderecoRepository.delete(usuario.endereco)
        if (usuarioRepository.findByEmailAndSenha(usuario.email, usuario.senha) == null) {
            return ResponseEntity.status(200).build()
        }
        return ResponseEntity.status(500).build()
    }

    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado")
        ]
    )
    @GetMapping("/listar")
    fun listar(): ResponseEntity<List<UsuarioDTO>> {
        val usuarios = usuarioRepository.findAll()
        val usuariosDTO = usuarios.map { usuarioParaDTO(it) }
        return if (usuariosDTO.isNotEmpty()) {
            ResponseEntity.status(200).body(usuariosDTO)
        } else {
            ResponseEntity.status(204).build()
        }
    }

    @Operation(summary = "Buscar usuário pelo ID", description = "Retorna o usuário correspondente ao ID fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        ]
    )
    @GetMapping("/buscar/{idUsuario}")
    fun get(@PathVariable idUsuario: Int): ResponseEntity<UsuarioDTO> {
        if (usuarioRepository.existsById(idUsuario)) {
            val usuario = usuarioRepository.findById(idUsuario).get()
            val usuarioDTO = usuarioParaDTO(usuario)
            return ResponseEntity.status(200).body(usuarioDTO)
        }
        return ResponseEntity.status(404).build()
    }


    @Operation(summary = "Login de usuário", description = "Autentica um usuário com email e senha")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            ApiResponse(responseCode = "401", description = "Credenciais inválidas")
        ]
    )
    @PostMapping("/login")
    fun login(@RequestBody loginData: Map<String, String>): ResponseEntity<Any> {
        val email = loginData["email"]
        val senha = loginData["senha"]

        if (email != null && senha != null) {
            val user = usuarioRepository.findByEmail(email)
            if (user != null && senha == user.senha) {
                return ResponseEntity.status(200).build()
            }
        }
        return ResponseEntity.status(401).build()
    } //falta fazer um login bom com autenticação

    @Operation(summary = "Buscar usuário pelo email", description = "Retorna o usuário correspondente ao email fornecido")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        ]
    )
    @GetMapping("/buscarPorEmail")
    fun buscarPorEmail(@RequestParam email: String): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioRepository.findByEmail(email)

        if (usuario != null) {
            val usuarioDTO = usuarioParaDTO(usuario)
            return ResponseEntity.status(200).body(usuarioDTO)
        }
        return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Atualizar informações do usuário", description = "Atualiza as informações do usuário")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado")
        ]
    )
    @PatchMapping("/atualizar/{idUsuario}")
    fun patchUsuario(
        @PathVariable idUsuario: Int,
        @RequestBody atualizarUsuario: Usuario
    ): ResponseEntity<UsuarioDTO> {
        if (usuarioRepository.existsById(idUsuario)) {
            val usuarioExistente = usuarioRepository.findById(idUsuario).get()

            atualizarUsuario.nomeUsuario?.let { usuarioExistente.nomeUsuario = it }
            atualizarUsuario.cpf?.let { usuarioExistente.cpf = it }
            atualizarUsuario.primeiroNome?.let { usuarioExistente.primeiroNome = it }
            atualizarUsuario.sobrenome?.let { usuarioExistente.sobrenome = it }
            atualizarUsuario.email?.let { usuarioExistente.email = it }

            usuarioExistente.dataAtualizacao = LocalDateTime.now()
            val usuarioAtualizado = usuarioRepository.save(usuarioExistente)
            val usuarioDTO = usuarioParaDTO(usuarioAtualizado)

            return ResponseEntity.status(200).body(usuarioDTO)
        }
        return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Atualizar a imagem de perfil de usuário", description = "Atualiza a imagem de perfil de um usuário pelo ID")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Imagem de perfil atualizada com sucesso")
        ]
    )
    @PatchMapping(value = ["/atualizar-imagem/{idUsuario}"],
        consumes = ["image/jpeg", "image/png", "image/jpg"])
    fun patchImagem(
        @PathVariable idUsuario: Int,
        @RequestBody novaFoto: ByteArray
    ):ResponseEntity<Void> {
        val usuario = usuarioRepository.findById(idUsuario).get()
        usuario.imagemPerfil = novaFoto
        usuarioRepository.save(usuario)

        return ResponseEntity.status(204).build()
    }

    fun usuarioParaDTO(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(
            idUsuario = usuario.idUsuario,
            nomeUsuario = usuario.nomeUsuario,
            cpf = usuario.cpf,
            primeiroNome = usuario.primeiroNome,
            sobrenome = usuario.sobrenome,
            email = usuario.email,
            dataCriacao = usuario.dataCriacao,
            deletado = usuario.deletado,
            dataDeletado = usuario.dataDeletado,
            dataAtualizacao = usuario.dataAtualizacao,
            endereco = usuario.endereco
        )
    }

}