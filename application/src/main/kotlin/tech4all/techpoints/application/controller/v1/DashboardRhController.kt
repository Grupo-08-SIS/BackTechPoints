package tech4all.techpoints.application.controller.v1

import tech4all.techpoints.domain.dto.AlunoDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

interface DashboardRhController {

    @GetMapping("/alunos")
    @Operation(
        summary = "Obter alunos por curso e cidade",
        description = "Retorna uma lista de alunos filtrados por curso e cidade"
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
            ApiResponse(responseCode = "400", description = "Erro de validação dos parâmetros"),
            ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        ]
    )
    fun getAlunos(
        @RequestParam(required = false) curso: String?,
        @RequestParam(required = false) cidade: String?
    ): ResponseEntity<List<AlunoDTO>>
}
