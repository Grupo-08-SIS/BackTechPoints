package techForAll.techPoints.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.dtos.CursoAlunosDto
import techForAll.techPoints.dtos.DemografiaDto

import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.DashboardAdmRepository


@Service
class DashboardAdmService@Autowired constructor(
    private val dashAdmRepositoy: DashboardAdmRepository,
    private val alunoRepository: AlunoRepository,
    private val pontuacaoService: PontuacaoService
){
    fun getAlunosPorCurso(): List<CursoAlunosDto> {
        return dashAdmRepositoy.findAlunosPorCurso()
    }

    fun getDemografiaAlunosPorLista(ids: List<Long>): DemografiaDto {
        val alunos = alunoRepository.findByIdIn(ids)
        val demografia = DemografiaDto()

        alunos.forEach { aluno ->
            demografia.processarAluno(aluno)
        }

        return demografia
    }

    fun getDemografiaPorTipoLista(tipoLista: String, idEmpresa: Long?): DemografiaDto {
        val ids = when (tipoLista) {
            "contratados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsContratadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsContratados()
            "interessados" -> if (idEmpresa != null) dashAdmRepositoy.findIdsInteressadosByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsInteressados()
            "processoSeletivo" -> if (idEmpresa != null) dashAdmRepositoy.findIdsProcessoSeletivoByEmpresa(idEmpresa) else dashAdmRepositoy.findIdsProcessoSeletivo()
            else -> throw IllegalArgumentException("Tipo de lista inválido")
        }
        val filteredIds = filtrarIds(ids)
        val demografia = getDemografiaAlunosPorLista(filteredIds)
        demografia.cursosFeitos.putAll(getCursosFeitosPorAlunos(filteredIds))
        return demografia
    }

    private fun filtrarIds(idStrings: List<Any>): List<Long> {
        return idStrings.flatMap { idString ->
            idString.toString()
                .removeSurrounding("[", "]")
                .split(", ")
                .map { it.toLong() }
        }.distinct()
    }

    private fun getCursosFeitosPorAlunos(ids: List<Long>): Map<String, Int> {
        val cursosFeitos = mutableMapOf<String, Int>()

        ids.forEach { idAluno ->
            val pontosPorCurso = pontuacaoService.recuperarPontosTotaisPorCurso(idAluno)
            pontosPorCurso.forEach { (_, cursoInfo) ->
                val nomeCurso = cursoInfo["nomeCurso"] as String
                val pontosTotais = cursoInfo["pontosTotais"] as Int
                if (pontosTotais > 0) {
                    cursosFeitos[nomeCurso] = cursosFeitos.getOrDefault(nomeCurso, 0) + 1
                }
            }
        }

        return cursosFeitos
    }
}