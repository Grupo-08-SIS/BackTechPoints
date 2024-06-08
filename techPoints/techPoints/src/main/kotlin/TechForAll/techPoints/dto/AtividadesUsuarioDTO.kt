package TechForAll.techPoints.dto

data class AtividadesUsuarioDTO(
    val idUsuario: Int,
    val nomeUsuario: String,
    val curso: String,
    val modulo: String,
    val totalAtividades: Int,
    val atividadesFeitas: Int
)
