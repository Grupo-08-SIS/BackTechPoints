//package techForAll.techPoints.mapper.impl
//
//import techForAll.techPoints.dto.AlunoCursoDTO
//import techForAll.techPoints.dto.AlunosPorCursoDTO
//import techForAll.techPoints.dto.RhAlunoCursoDto
//import techForAll.techPoints.dto.RhAlunoCursoDtoOrdenado
//import techForAll.techPoints.mapper.MapperDashboardRh
//
//class MapperDashboardRhImpl : MapperDashboardRh {
//
//    override fun mapToAlunosCursoDto(alunos: Map<Pair<Int, String>, List<RhAlunoCursoDto>>): List<RhAlunoCursoDtoOrdenado> {
//
//      return alunos.map {
//            (cursoIdNome, alunos) ->
//          RhAlunoCursoDtoOrdenado(
//                idCurso = cursoIdNome.first,
//                nomeCurso = cursoIdNome.second,
//                alunosFiltrados = alunos.map {
//                    RhAlunoCursoDto(
//                        idCurso = cursoIdNome.first,
//                        nomeCurso = cursoIdNome.second,
//                        idUsuario = it.idUsuario,
//                        nomeUsuario = it.nomeUsuario,
//                        municipio = it.municipio
//                    )
//                }
//
//        ) }
//    }
//
//}