package tech4all.techpoints.domain.service.impl

import org.springframework.stereotype.Service
import tech4all.techpoints.domain.persistence.DashboardRhPersistence
import tech4all.techpoints.domain.service.DashboardRhService
import tech4all.techpoints.domain.dto.AlunoDTO

@Service
class DashboardRhServiceImpl(
    private val dashboardRhPersistence: DashboardRhPersistence
) : DashboardRhService {

    override fun findAlunosByCursoAndCidade(curso: String?, cidade: String?): List<AlunoDTO> {
        val results = dashboardRhPersistence.findAlunosByCursoAndCidade(curso, cidade)
        return results.map { result ->
            AlunoDTO(
                idUsuario = (result.id ?: 0),
                nomeUsuario = result.nomeUsuario ?: "",
                primeiroNome = result.primeiroNome ?: "",
                sobrenome = result.sobrenome ?: "",
                email = result.email ?: "",
                nomeCurso = result.nomeCurso ?: "",
                cidade = result.cidade ?: ""
            )
        }
    }
}
