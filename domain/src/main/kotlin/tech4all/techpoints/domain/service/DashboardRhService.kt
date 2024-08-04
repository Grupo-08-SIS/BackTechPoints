package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.dto.AlunoDTO

interface DashboardRhService {

    fun findAlunosByCursoAndCidade(curso: String?, cidade: String?): List<AlunoDTO>

}
