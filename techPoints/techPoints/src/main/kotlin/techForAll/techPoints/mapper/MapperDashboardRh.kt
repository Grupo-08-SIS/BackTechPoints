package techForAll.techPoints.mapper

import techForAll.techPoints.dominio.Usuario
import techForAll.techPoints.dto.RhAlunoCursoDto
import techForAll.techPoints.dto.RhAlunoCursoDtoOrdenado
import techForAll.techPoints.dto.rhdashboard.AlunoEspecificoDto

interface MapperDashboardRh {

    fun mapToAlunosCursoDto(alunos: Map<Pair<Int, String>, List<RhAlunoCursoDto>>): List<RhAlunoCursoDtoOrdenado>;

    fun usuarioToAlunoEspecificoDto(usuario: RhAlunoCursoDto): AlunoEspecificoDto;

}

