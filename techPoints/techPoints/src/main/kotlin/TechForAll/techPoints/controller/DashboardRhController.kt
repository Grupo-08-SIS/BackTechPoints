package TechForAll.techPoints.controller

import TechForAll.techPoints.dto.AlunoDTO
import TechForAll.techPoints.service.DashboardRhService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/dashboardRh")
@Validated
class DashboardRhController (
    private val dashboardRhService: DashboardRhService
){

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
    @GetMapping("/alunos")
    fun getAlunos(
        @RequestParam(required = false) curso: String?,
        @RequestParam(required = false) cidade: String?
    ): ResponseEntity<Any> {
        return try {
            val alunos = dashboardRhService.getUsuariosPorCursoEMunicipio(curso, cidade)
            ResponseEntity.ok(alunos)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        }
    }
}