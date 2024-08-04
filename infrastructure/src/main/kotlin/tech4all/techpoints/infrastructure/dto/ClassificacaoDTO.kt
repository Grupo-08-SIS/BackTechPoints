package tech4all.techpoints.infrastructure.dto

data class ClassificacaoDTO(
    val id: Int,
    val nomeUsuario: String,
    val email: String,
    val totalPontos: Long,
    val ranking: Int
)