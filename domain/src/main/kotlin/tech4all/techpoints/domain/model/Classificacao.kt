package tech4all.techpoints.domain.model

data class Classificacao(
    val id: Int,
    val nomeUsuario: String,
    val email: String,
    val totalPontos: Long,
    val ranking: Int
)