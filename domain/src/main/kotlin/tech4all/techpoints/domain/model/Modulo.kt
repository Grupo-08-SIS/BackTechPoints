package tech4all.techpoints.domain.model


import jakarta.persistence.*

data class Modulo(

    val idModulo: Int,

    val cursoId: Int,

    val qtdAtividadeTotal: Int,

    val nomeModulo: String?
)