package tech4all.techpoints.infrastructure.dto

data class AtividadesPorCursoEUsuarioDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val totalAtividadesFeitas: Long
)