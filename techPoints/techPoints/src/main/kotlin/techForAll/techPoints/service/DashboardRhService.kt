package techForAll.techPoints.service

import techForAll.techPoints.repository.DashboardRhRepository
import org.springframework.stereotype.Service
import techForAll.techPoints.dto.AlunoCursoDTO
import techForAll.techPoints.dto.AlunoDTO
import techForAll.techPoints.dto.AlunosPorCursoDTO
import techForAll.techPoints.dto.CursoComAlunoDTO

@Service
class DashboardRhService (
    private val dashboardRhRepository: DashboardRhRepository
){
    fun getCursosComUsuarios(): List<AlunosPorCursoDTO> {
        val resultados = dashboardRhRepository.findAllCursosComUsuarios()

        return resultados.groupBy { it.idCurso to it.nomeCurso }.map { (cursoIdNome, alunos) ->
            AlunosPorCursoDTO(
                idCurso = cursoIdNome.first,
                nomeCurso = cursoIdNome.second,
                alunos = alunos.filter { it.idUsuario != null && it.nomeUsuario != null }.map {
                    AlunoCursoDTO(
                        idUsuario = it.idUsuario!!,
                        nomeUsuario = it.nomeUsuario!!
                    )
                }
            )
        }
    }
}