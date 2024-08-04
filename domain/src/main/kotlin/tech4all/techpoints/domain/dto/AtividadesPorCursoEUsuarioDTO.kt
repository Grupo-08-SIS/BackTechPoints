package tech4all.techpoints.domain.dto

data class AtividadesPorCursoEUsuarioDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val totalAtividadesFeitas: Long
)