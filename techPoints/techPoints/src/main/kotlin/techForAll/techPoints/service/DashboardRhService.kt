package techForAll.techPoints.service

import techForAll.techPoints.repository.DashboardRhRepository
import org.springframework.stereotype.Service
import techForAll.techPoints.dto.*
import techForAll.techPoints.mapper.impl.MapperDashboardRhImpl

@Service
class DashboardRhService (
    private val dashboardRhRepository: DashboardRhRepository,
    private val mapperDashboardRh: MapperDashboardRhImpl
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

    fun getAlunoEspecificoModal(id: Int): AlunoEspecificoDto? {

        val alunoEncontrado = dashboardRhRepository.findById(id);

        if( alunoEncontrado.isPresent ) {
            val alunoDto = alunoEncontrado.map { mapperDashboardRh.usuarioToAlunoEspecificoDto(it) };
            return alunoDto.get();
        } else {
            return null;
        }
    }

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

    fun getAlunosFiltrado(escolaridade: String?, municipio: String?, nomeCurso: String?): List<RhAlunoCursoDtoOrdenado> {

        //TODO: Escolaridade
        var alunos = dashboardRhRepository.findAllUsuariosComCurso(municipio, nomeCurso);

        if (municipio == null && escolaridade == null && nomeCurso == null) {
            throw IllegalArgumentException("Pelo menos um parâmetro de filtro deve ser fornecido.")
            // TODO: ERRO
        } else {
            if (municipio != null) {
                alunos = alunos.filter { it.municipio == municipio };
            }
            //TODO: Escolaridade
            //        if (escolaridade.isNotEmpty()) {

            if (nomeCurso != null) {
                alunos = alunos.filter { it.nomeCurso == nomeCurso }
            }
        }

        val alunosFiltrados = alunos.sortedBy { it.nomeUsuario }
            .groupBy { it.idCurso to it.nomeCurso };

        return mapperDashboardRh.mapToAlunosCursoDto(alunosFiltrados);

    }
}
}
