package tech4all.techpoints.domain.model

data class PontosSemana(
    val idUsuario: Int,
    val nomeUsuario: String,
    val pontosSemanaAtual: Double,
    val pontosSemanaAnterior: Double,
    val variacaoPontos: Double
)

