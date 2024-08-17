package techForAll.techPoints.mapper

import techForAll.techPoints.dto.RhAlunoCursoDto
import techForAll.techPoints.dto.RhAlunoCursoDtoOrdenado

interface MapperDashboardRh {

    fun mapToAlunosCursoDto(alunos: Map<Pair<Int, String>, List<RhAlunoCursoDto>>): List<RhAlunoCursoDtoOrdenado>
}