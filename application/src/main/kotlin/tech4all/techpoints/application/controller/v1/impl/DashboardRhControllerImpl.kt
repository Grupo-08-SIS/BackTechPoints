package tech4all.techpoints.application.controller.v1.impl

import tech4all.techpoints.application.controller.v1.DashboardRhController
import tech4all.techpoints.domain.service.DashboardRhService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech4all.techpoints.domain.dto.AlunoDTO

@RestController
@RequestMapping("/v1/dashboardRh")
@Tag(name = "DashboardRH", description = "Dashboard para dados RH")
class DashboardRhControllerImpl(
    private val dashboardRhService: DashboardRhService
) : DashboardRhController {

    override fun getAlunos(
        curso: String?,
        cidade: String?
    ): ResponseEntity<List<AlunoDTO>> {
        val alunos = dashboardRhService.findAlunosByCursoAndCidade(curso, cidade)
        return ResponseEntity.ok(alunos)
    }
}
