package techForAll.techPoints.dto

data class AlunosPorCursoDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val alunos: List<AlunoCursoDTO>
)

