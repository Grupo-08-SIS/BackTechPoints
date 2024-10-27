package techForAll.techPoints.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import techForAll.techPoints.service.NotificacaoService

@RestController
@RequestMapping("/notificacoes")
class NotificacaoController(
    private val notificacaoService: NotificacaoService
) {

    @Operation(
        summary = "Listar todas as notificações de um aluno",
        description = "Retorna uma lista de todas as notificações para o aluno especificado pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de notificações retornada com sucesso"),
            ApiResponse(responseCode = "204", description = "Nenhuma notificação encontrada"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/{idAluno}")
    fun listarNotificacoesPorAluno(@PathVariable idAluno: Long): ResponseEntity<Any> {
        return try {
            val notificacoes = notificacaoService.getNotificacoesPorAluno(idAluno)
            if (notificacoes.isNotEmpty()) {
                ResponseEntity.status(200).body(notificacoes)
            } else {
                ResponseEntity.status(204).build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor: ${e.message}"))
        }
    }
}