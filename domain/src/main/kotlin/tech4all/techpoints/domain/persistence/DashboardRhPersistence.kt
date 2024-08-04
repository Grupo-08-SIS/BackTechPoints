package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.Aluno
import java.util.Optional

interface DashboardRhPersistence {

    fun findAlunosByCursoAndCidade(curso: String?, cidade: String?): List<Aluno>

}
