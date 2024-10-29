package techForAll.techPoints.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @Operation(
        summary = "Marcar notificação como lida",
        description = "Marca a notificação especificada pelo ID como lida para o aluno especificado pelo ID"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Notificação marcada como lida com sucesso"),
            ApiResponse(responseCode = "404", description = "Notificação ou aluno não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PatchMapping("/{idAluno}/notificacoes/{idNotificacao}/marcar-como-lida")
    fun marcarNotificacaoComoLida(
        @PathVariable idAluno: Long,
        @PathVariable idNotificacao: Long
    ): ResponseEntity<Any> {
        return try {
            val notificacao = notificacaoService.marcarNotificacaoComoLida(idAluno, idNotificacao)
            ResponseEntity.status(200).body(notificacao)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Notificação ou aluno não encontrado"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor: ${e.message}"))
        }
    }
}