package tech4all.techpoints.domain.model

import jakarta.persistence.*

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class Endereco (

    var id: Int,

    var cep: String,

    var cidade: String,

    var estado: String,

    var rua: String,

    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),
)