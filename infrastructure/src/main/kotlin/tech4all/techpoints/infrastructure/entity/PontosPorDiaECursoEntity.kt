package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "PontosPorDiaEporCursoPorUsuario")
data class PontosPorDiaECursoEntity(
    @Id val usuarioId: Int,
    val cursoId: Int,
    val nomeCurso: String,
    val data: LocalDate,
    val pontos: Long
)
