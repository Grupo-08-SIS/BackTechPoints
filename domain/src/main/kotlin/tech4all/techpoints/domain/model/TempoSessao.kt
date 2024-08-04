package tech4all.techpoints.domain.model

import jakarta.persistence.*

data class TempoSessao (

    var idTempoSessao: Int,

    var diaSessao: String,

    var qtdTempo: String,

    var fkUsuario: Usuario?
)