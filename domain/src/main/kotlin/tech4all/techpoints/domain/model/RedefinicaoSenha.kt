package tech4all.techpoints.domain.model

import java.time.LocalDateTime
import java.util.*

data class RedefinicaoSenha(

    val idRedefinicaoSenha: Int?,

    val codigoRedefinicao: String,

    val dataCriacao: LocalDateTime,

    val dataExpiracao: LocalDateTime,

    val valido: Boolean,

    val emailRedefinicao: String,

    var fkUsuario: Optional<Usuario>
)