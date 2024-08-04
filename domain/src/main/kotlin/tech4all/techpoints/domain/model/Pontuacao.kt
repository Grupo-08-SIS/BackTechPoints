package tech4all.techpoints.domain.model

import jakarta.persistence.*
import java.time.LocalDateTime

data class Pontuacao(

    val idPontuacao: Int,

    val usuario: Usuario,

    val ponto: Ponto,

    val totalPontosUsuario: Int,

    val dataAtualizacao: LocalDateTime
)
