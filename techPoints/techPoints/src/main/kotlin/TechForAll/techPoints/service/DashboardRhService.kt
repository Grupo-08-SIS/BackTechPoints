package TechForAll.techPoints.service

import TechForAll.techPoints.dto.AlunoDTO
import TechForAll.techPoints.repository.DashboardRhRepository
import org.springframework.stereotype.Service

@Service
class DashboardRhService (
    private val dashboardRhRepository: DashboardRhRepository
){
    fun getUsuariosPorCursoEMunicipio(curso: String?, cidade: String?): List<AlunoDTO> {
        val results = dashboardRhRepository.findAlunosByCursoAndCidade(curso, cidade)
        return results.map { result ->
            val row = result as Array<Any>
            AlunoDTO(
                idUsuario = (row[0] as Int?) ?: 0,
                nomeUsuario = row[1] as String?,
                primeiroNome = row[2] as String?,
                sobrenome = row[3] as String?,
                email = row[4] as String?,
                nomeCurso = row[5] as String?,
                cidade = row[6] as String?
            )
        }
    }
}