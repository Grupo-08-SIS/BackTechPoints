package techForAll.techPoints.dtos


data class MetaDiariaDto(

    var id: Long,

    var nomeDia: String,

    var qtdTempoEstudo: String,

    var ativado: Boolean,

    var metaAtingida: Boolean = false,

    var metaEstudoSemana: Long
)
