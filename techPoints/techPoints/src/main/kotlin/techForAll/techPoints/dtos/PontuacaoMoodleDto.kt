package techForAll.techPoints.dtos

data class PontuacaoMoodleDto(
    val alunoId: Long,
    val alunoEmail: String,
    val cursoNome: String,
    val dataEntrega: String,
    val nomeAtividade: String,
    val notaAtividade: Double,
    val notaAluno: Double?
)
