package techForAll.techPoints.dtos

data class CursoMoodleDto(
    val id: Long,
    val nome: String,
    val totalAtividades: Int,
    val totalAtividadesDoAluno: Int
)