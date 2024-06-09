package TechForAll.techPoints.service

import TechForAll.techPoints.dto.AlunoDTO
import TechForAll.techPoints.repository.DashboardRhRepository
import org.springframework.stereotype.Service

@Service
class DashboardRhService (
    private val dashboardRhRepository: DashboardRhRepository
){
//    fun getUsuariosPorCursoEMunicipio(nomeCurso: String?, cidade: String?): List<AlunoDTO> {
//        return dashboardRhRepository.findUsuariosByCursoAndCidade(nomeCurso, cidade)
//    }
}