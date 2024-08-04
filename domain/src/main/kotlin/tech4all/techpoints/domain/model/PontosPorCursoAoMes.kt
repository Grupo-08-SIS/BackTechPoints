package tech4all.techpoints.domain.model

data class PontosPorCursoAoMes(
    val mes: Int,
    val cursoId: Int,
    val nomeCurso: String,
    val totalPontos: Double
)
