package tech4all.techpoints.domain.dto

data class PontosSemanaDTO(
    val idUsuario: Int,
    val nomeUsuario: String,
    val pontosSemanaAtual: Double,
    val pontosSemanaPassada: Double,
    val diferencaPontos: Double
)
