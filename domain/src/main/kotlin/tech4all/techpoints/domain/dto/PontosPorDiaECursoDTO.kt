package tech4all.techpoints.domain.dto

import java.time.LocalDate

data class PontosPorDiaECursoDTO(
    val usuarioId: Int,
    val cursoId: Int,
    val nomeCurso: String,
    val data: LocalDate,
    val pontos: Long
)
