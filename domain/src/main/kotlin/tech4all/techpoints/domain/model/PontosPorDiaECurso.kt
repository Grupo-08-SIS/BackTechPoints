package tech4all.techpoints.domain.model

import java.time.LocalDate

data class PontosPorDiaECurso(
    val usuarioId: Int,
    val cursoId: Int,
    val nomeCurso: String,
    val data: LocalDate,
    val pontos: Double
)
