package techForAll.techPoints.dtos

data class PontuacaoComPontosDTO(
    val id: Long,
    val dataEntrega: String,
    val nomeAtividade: String,
    val notaAtividade: Double,
    val notaAluno: Double,
    val pontosAtividade: Int,
    val cursoId: Long,
    val alunoId: Long
)