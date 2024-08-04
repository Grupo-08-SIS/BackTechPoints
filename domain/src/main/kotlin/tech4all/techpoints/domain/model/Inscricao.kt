package tech4all.techpoints.domain.model

import jakarta.persistence.*

data class Inscricao(

    val id: Long? = null,

    val curso: Curso,

    val codigoInscricao: String? = null,

    val usuario: Usuario? = null,
)


