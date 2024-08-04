package tech4all.techpoints.application.controller.v1

import tech4all.techpoints.application.dto.EmailRequest
import tech4all.techpoints.application.dto.ResponseMessageDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface ResetSenhaController {

    @PostMapping("/solicitar-troca")
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
    fun solicitarTrocaSenha(
        @RequestBody @Valid emailRequest: EmailRequest
    ): ResponseEntity<ResponseMessageDTO>

    @PostMapping("/verificar-token")
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
    fun verificarToken(
        @RequestBody tokenRequest: Map<String, String>
    ): ResponseEntity<ResponseMessageDTO>

    @PatchMapping("/nova-senha")
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
    fun atualizarSenha(
        @RequestBody senhaRequest: Map<String, String>
    ): ResponseEntity<ResponseMessageDTO>
}
