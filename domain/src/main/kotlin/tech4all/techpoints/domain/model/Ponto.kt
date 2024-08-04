package tech4all.techpoints.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

data class Ponto(

    val id: Int,

    val nota: Int,

    val qtdPonto: Int,

    val dataEntrega: LocalDateTime,

    val referenciaGerarPontuacao: ReferenciaGerarPontuacao,

    val atividadeEntity: Atividade,

    val usuario: Usuario
)