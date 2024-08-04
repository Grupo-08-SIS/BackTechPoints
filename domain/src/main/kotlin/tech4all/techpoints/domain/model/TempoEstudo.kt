package tech4all.techpoints.domain.model

import jakarta.persistence.*

data class TempoEstudo (

    var idTempoEstudo: Int,

    var nomeDia: String,

    var qtdTempoEstudo: String,

    var ativado: Boolean,

    var metaAtingida: Boolean,


)
