package tech4all.techpoints.domain.model

import jakarta.persistence.*

data class ReferenciaGerarPontuacao(

    val id: Int,

    val nomeDaGeracao: String?,

    val descricaoDaGeracao: String?
)
