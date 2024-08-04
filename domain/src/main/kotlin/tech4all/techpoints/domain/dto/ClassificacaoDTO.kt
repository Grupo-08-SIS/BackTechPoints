package tech4all.techpoints.domain.dto

data class ClassificacaoDTO(
    val id: Int,
    val nomeUsuario: String,
    val email: String,
    val totalPontos: Long,
    val ranking: Int
)