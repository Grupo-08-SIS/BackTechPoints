package techForAll.techPoints.dto



data class PontosSemanaDTO(
    val idUsuario: Int,
    val nomeUsuario: String,
    val pontosSemanaAtual: Int,
    val pontosSemanaPassada: Int,
    val diferencaPontos: Int
)