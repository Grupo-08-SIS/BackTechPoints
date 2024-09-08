package techForAll.techPoints.dto

data class CursoComAlunoDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val idUsuario: Int?,
    val nomeUsuario: String?
)
