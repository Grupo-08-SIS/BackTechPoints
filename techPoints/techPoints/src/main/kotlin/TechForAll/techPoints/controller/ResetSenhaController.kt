package TechForAll.techPoints.controller

import TechForAll.techPoints.repository.RedefinicaoSenhaRepository
import TechForAll.techPoints.repository.UsuarioRepository
import TechForAll.techPoints.service.UsuarioService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/reset-senha")
class ResetSenhaController (private var usuarioService: UsuarioService)
{
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Autowired
    lateinit var senhaRepository: RedefinicaoSenhaRepository

    //alterar para request body depois, RequestParam é altamente inseguro
    @Operation(summary = "Solicitar troca de senha",
        description = "Endpoint para solicitar troca de senha.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Troca de senha solicitada com sucesso."),
        ApiResponse(responseCode = "400", description = "Erro ao solicitar troca de senha."),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    @PostMapping("/solicitar-troca")
    fun solicitarTrocaSenha(@RequestParam("email") emailUser: String): ResponseEntity<Any> {
        var verificar = usuarioRepository.existsByEmail(emailUser)
        if (verificar == true){
            return usuarioService.senhaReset(emailUser)
        }
       return ResponseEntity.status(404).build()
    }

    @Operation(summary = "Verificar token de redefinição de senha",
        description = "Endpoint para verificar a validade do token de redefinição de senha.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Token de redefinição de senha válido."),
        ApiResponse(responseCode = "400", description = "Token de redefinição de senha inválido.")
    ])
    @PostMapping("/verificar-token")
    fun verificarToken(@RequestParam("codigo") codigoRedefinicao: String, @RequestParam("email") emailUser: String): ResponseEntity<Any> {
        val existeRedefinicao = senhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao, emailUser)
        return if (existeRedefinicao == true) {
            ResponseEntity.status(200).build()
        } else {
            ResponseEntity.status(400).body("Não há uma redefinição de senha válida para o email e token fornecidos")
        }
    } //voltando o "ok" o front redireciona para uma pagina com a possibilidade de alterar a senha

    @Operation(summary = "Atualizar senha do usuário",
        description = "Endpoint para atualizar a senha do usuário.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Senha do usuário atualizada com sucesso."),
        ApiResponse(responseCode = "400", description = "Erro ao atualizar a senha do usuário.")
    ])
    @PatchMapping("/nova-senha")
    fun patch(@RequestParam("email") emailUser: String, @RequestBody novaSenha: String): ResponseEntity<Any> {
            val usuario = usuarioRepository.findByEmail(emailUser)
            usuario.senha = novaSenha.toString()
            usuario.dataAtualizacao = LocalDateTime.now()
            usuarioRepository.save(usuario)
            return ResponseEntity.status(200).build()
    }
}