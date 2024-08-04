package tech4all.techpoints.domain.dto

data class PontosPorCursoAoMesDTO(
    val mes: Int,
    val idCurso: Int,
    val nomeCurso: String,
    val totalPontos: Double
)
