package techForAll.techPoints.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import techForAll.techPoints.service.DashboardRecrutadorService

@RestController
@RequestMapping("/dashboardRecrutador")
class DashboardRecrutadorController@Autowired constructor(
    private val recrutadorService: DashboardRecrutadorService
) {

    @Operation(summary = "Adicionar aluno aos favoritos", description = "Adiciona um aluno à lista de favoritos de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Aluno adicionado aos favoritos com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador ou Aluno não encontrado"),
            ApiResponse(responseCode = "409", description = "Aluno já está na lista de favoritos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/{idRecrutador}/favoritos/{idAluno}")
    fun adicionarFavorito(
        @PathVariable idRecrutador: Long,
        @PathVariable idAluno: Long
    ): ResponseEntity<Any> {
        return try {
            recrutadorService.adicionarFavorito(idRecrutador, idAluno)
            ResponseEntity.status(200).build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador ou Aluno não encontrado"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(409).body(mapOf("message" to "Aluno já está na lista de favoritos"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor ${e.message}"))
        }
    }

    @Operation(summary = "Adicionar aluno aos interessados", description = "Adiciona um aluno à lista de interessados de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Aluno adicionado à lista de interessados com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador ou Aluno não encontrado"),
            ApiResponse(responseCode = "409", description = "Aluno já está na lista de interessados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @PostMapping("/{idRecrutador}/interessados/{idAluno}")
    fun adicionarInteressado(
        @PathVariable idRecrutador: Long,
        @PathVariable idAluno: Long
    ): ResponseEntity<Any> {
        return try {
            recrutadorService.adicionarInteressado(idRecrutador, idAluno)
            ResponseEntity.status(200).build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador ou Aluno não encontrado"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(409).body(mapOf("message" to "Aluno já está na lista de interessados"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor ${e.message}"))
        }
    }


    @Operation(summary = "Remover aluno dos favoritos", description = "Remove um aluno da lista de favoritos de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Aluno removido dos favoritos com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador ou Aluno não encontrado"),
            ApiResponse(responseCode = "409", description = "Aluno não está na lista de favoritos"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping("/{idRecrutador}/favoritos/{idAluno}")
    fun removerFavorito(
        @PathVariable idRecrutador: Long,
        @PathVariable idAluno: Long
    ): ResponseEntity<Any> {
        return try {
            recrutadorService.removerFavorito(idRecrutador, idAluno)
            ResponseEntity.status(200).build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador ou Aluno não encontrado"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(409).body(mapOf("message" to "Aluno não está na lista de favoritos"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor ${e.message}"))
        }
    }

    @Operation(summary = "Remover aluno dos interessados", description = "Remove um aluno da lista de interessados de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Aluno removido da lista de interessados com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador ou Aluno não encontrado"),
            ApiResponse(responseCode = "409", description = "Aluno não está na lista de interessados"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @DeleteMapping("/{idRecrutador}/interessados/{idAluno}")
    fun removerInteressado(
        @PathVariable idRecrutador: Long,
        @PathVariable idAluno: Long
    ): ResponseEntity<Any> {
        return try {
            recrutadorService.removerInteressado(idRecrutador, idAluno)
            ResponseEntity.status(200).build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador ou Aluno não encontrado"))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(409).body(mapOf("message" to "Aluno não está na lista de interessados"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor ${e.message}"))
        }
    }

    @Operation(summary = "Listar favoritos", description = "Lista todos os alunos favoritos de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de favoritos retornada com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/{idRecrutador}/listar/favoritos")
    fun listarFavoritos(@PathVariable idRecrutador: Long): ResponseEntity<Any> {
        return try {
            val favoritos = recrutadorService.listarFavoritos(idRecrutador)
            ResponseEntity.ok(favoritos)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador não encontrado"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    @Operation(summary = "Listar interessados", description = "Lista todos os alunos interessados de um recrutador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de interessados retornada com sucesso"),
            ApiResponse(responseCode = "404", description = "Recrutador não encontrado"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    @GetMapping("/{idRecrutador}/listar/interessados")
    fun listarInteressados(@PathVariable idRecrutador: Long): ResponseEntity<Any> {
        return try {
            val interessados = recrutadorService.listarInteressados(idRecrutador)
            ResponseEntity.ok(interessados)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(404).body(mapOf("message" to "Recrutador não encontrado"))
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }
}
