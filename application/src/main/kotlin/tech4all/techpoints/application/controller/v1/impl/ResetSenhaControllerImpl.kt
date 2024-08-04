package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.ResetSenhaController
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech4all.techpoints.application.dto.EmailRequest
import tech4all.techpoints.application.dto.ResponseMessageDTO
import tech4all.techpoints.domain.exception.InvalidTokenException
import tech4all.techpoints.domain.exception.UserNotFoundException
import tech4all.techpoints.domain.service.ResetSenhaService

@RestController
@RequestMapping("/v1/reset-senha")
@Tag(name = "ResetSenha", description = "Endpoints para reset de senha")
class ResetSenhaControllerImpl(
    private val resetService: ResetSenhaService
) : ResetSenhaController {

    override fun solicitarTrocaSenha(emailRequest: EmailRequest): ResponseEntity<ResponseMessageDTO> {
        return try {
            resetService.senhaReset(emailRequest.email)
            ResponseEntity.ok(ResponseMessageDTO("Troca de senha solicitada com sucesso."))
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(404).body(ResponseMessageDTO("Usuário não encontrado"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(ResponseMessageDTO("Erro interno do servidor"))
        }
    }

    override fun verificarToken(tokenRequest: Map<String, String>): ResponseEntity<ResponseMessageDTO> {
        val codigoRedefinicao = tokenRequest["codigo"]
        val emailUser = tokenRequest["email"]
        return if (codigoRedefinicao != null && emailUser != null) {
            return try {
                resetService.verificarToken(codigoRedefinicao, emailUser)
                ResponseEntity.ok(ResponseMessageDTO("Token de redefinição de senha válido."))
            } catch (e: InvalidTokenException) {
                ResponseEntity.badRequest().body(ResponseMessageDTO("Token de redefinição de senha inválido."))
            } catch (e: Exception) {
                ResponseEntity.status(500).body(ResponseMessageDTO("Erro interno do servidor"))
            }
        } else {
            ResponseEntity.badRequest().body(ResponseMessageDTO("Dados de solicitação inválidos"))
        }
    }

    override fun atualizarSenha(senhaRequest: Map<String, String>): ResponseEntity<ResponseMessageDTO> {
        val emailUser = senhaRequest["email"]
        val novaSenha = senhaRequest["novaSenha"]
        return if (emailUser != null && novaSenha != null) {
            return try {
                resetService.atualizarSenha(emailUser, novaSenha)
                ResponseEntity.ok(ResponseMessageDTO("Senha do usuário atualizada com sucesso."))
            } catch (e: UserNotFoundException) {
                ResponseEntity.status(404).body(ResponseMessageDTO("Usuário não encontrado"))
            } catch (e: Exception) {
                ResponseEntity.status(500).body(ResponseMessageDTO("Erro interno do servidor"))
            }
        } else {
            ResponseEntity.badRequest().body(ResponseMessageDTO("Dados de solicitação inválidos"))
        }
    }
}
