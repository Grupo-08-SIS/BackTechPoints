package tech4all.techpoints.infrastructure.dto

data class AtividadesUsuarioDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val totalQtdAtividades: Long?,
    val totalAtividadesUsuario: Long
)