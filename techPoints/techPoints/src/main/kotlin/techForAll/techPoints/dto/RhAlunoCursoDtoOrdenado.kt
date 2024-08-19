package techForAll.techPoints.dto

data class RhAlunoCursoDtoOrdenado (
    val idCurso: Int,
    val nomeCurso: String,
    val alunosFiltrados: List<RhAlunoCursoDto>
)