package tech4all.techpoints.domain.dto

import java.time.LocalDateTime

data class RedefinicaoSenhaDTO(
    val id: Int,
    val codigo: String,
    val dataCriacao: LocalDateTime,
    val dataExpiracao: LocalDateTime,
    val valido: Boolean
)
