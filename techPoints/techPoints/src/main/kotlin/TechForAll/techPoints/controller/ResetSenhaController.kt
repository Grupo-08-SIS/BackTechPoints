package TechForAll.techPoints.controller

import TechForAll.techPoints.dominio.EmailRequest
import TechForAll.techPoints.repository.RedefinicaoSenhaRepository
import TechForAll.techPoints.repository.UsuarioRepository
import TechForAll.techPoints.service.ResetSenhaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/reset-senha")
class ResetSenhaController @Autowired constructor(
    private val resetService: ResetSenhaService,
    private val usuarioRepository: UsuarioRepository,
) {

    @Operation(
        summary = "Solicitar troca de senha",
        description = "Endpoint para solicitar troca de senha."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Troca de senha solicitada com sucesso."),
            ApiResponse(responseCode = "400", description = "Erro ao solicitar troca de senha."),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/solicitar-troca")
    fun solicitarTrocaSenha(@RequestBody @Valid emailRequest: EmailRequest): ResponseEntity<Any> {
        return try {
            val emailUser = emailRequest.email
            if (usuarioRepository.existsByEmail(emailUser)) {
                resetService.senhaReset(emailUser)
            } else {
                ResponseEntity.status(404).body(mapOf("message" to "Usuário não encontrado"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    @Operation(
        summary = "Verificar token de redefinição de senha",
        description = "Endpoint para verificar a validade do token de redefinição de senha."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Token de redefinição de senha válido."),
            ApiResponse(responseCode = "400", description = "Token de redefinição de senha inválido."),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/verificar-token")
    fun verificarToken(@RequestBody tokenRequest: Map<String, String>): ResponseEntity<Any> {
        val codigoRedefinicao = tokenRequest["codigo"]
        val emailUser = tokenRequest["email"]
        return if (codigoRedefinicao != null && emailUser != null) {
            resetService.verificarToken(codigoRedefinicao, emailUser)
        } else {
            ResponseEntity.badRequest().body(mapOf("message" to "Dados de solicitação inválidos"))
        }
    }

    @Operation(
        summary = "Atualizar senha do usuário",
        description = "Endpoint para atualizar a senha do usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Senha do usuário atualizada com sucesso."),
            ApiResponse(responseCode = "400", description = "Erro ao atualizar a senha do usuário."),
            ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping("/nova-senha")
    fun atualizarSenha(@RequestBody senhaRequest: Map<String, String>): ResponseEntity<Any> {
        val emailUser = senhaRequest["email"]
        val novaSenha = senhaRequest["novaSenha"]
        return if (emailUser != null && novaSenha != null) {
            resetService.atualizarSenha(emailUser, novaSenha)
        } else {
            ResponseEntity.status(400).body(mapOf("message" to "Dados de solicitação inválidos"))
        }
    }
}
