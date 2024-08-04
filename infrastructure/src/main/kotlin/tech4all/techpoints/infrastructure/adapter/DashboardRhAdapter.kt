package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.AlunoDTO
import tech4all.techpoints.infrastructure.repository.DashboardRhRepository

@Component
class DashboardRhAdapter(
    private val dashboardRhRepository: DashboardRhRepository
) {

    fun findAlunosByCursoAndCidade(curso: String?, cidade: String?): List<AlunoDTO> {
        return dashboardRhRepository.findAlunosByCursoAndCidade(curso, cidade)
    }
}
