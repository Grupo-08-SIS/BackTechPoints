package tech4all.techpoints.domain.dto

data class AtividadesUsuarioDTO(
    val idCurso: Int,
    val nomeCurso: String,
    val totalQtdAtividades: Long?,
    val totalAtividadesUsuario: Long
)