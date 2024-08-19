package techForAll.techPoints.controller

import techForAll.techPoints.service.DashboardRhService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import techForAll.techPoints.dto.rhdashboard.AlunoEspecificoDto


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
            val alunos = dashboardRhService.getCursosComUsuarios()
            ResponseEntity.ok(alunos)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(400).body(mapOf("message" to e.message))
        }
    }

    @GetMapping("/aluno/{id}")
    fun getAlunoModal(
        @PathVariable id: Int
    ): ResponseEntity<AlunoEspecificoDto> {
        val aluno = dashboardRhService.getAlunoEspecificoModal(id);

        return if (aluno !== null) {
            ResponseEntity.status(200).body(aluno);

        } else {
            ResponseEntity.status(404).build();
        }
    }
}