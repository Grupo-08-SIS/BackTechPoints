package tech4all.techpoints.infrastructure.dto

data class PontosPorCursoAoMesDTO(
    val mes: Int,
    val idCurso: Int,
    val nomeCurso: String,
    val totalPontos: Double
)
