package TechForAll.techPoints.controller

import TechForAll.techPoints.dto.AlunoDTO
import TechForAll.techPoints.service.DashboardRhService
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

//    @GetMapping("/alunos")
//    fun getAlunos(
//        @RequestParam(required = false) curso: String?,
//        @RequestParam(required = false) cidade: String?
//    ): List<AlunoDTO> {
//        return dashboardRhService.getUsuariosPorCursoEMunicipio(curso, cidade)
//    }
}