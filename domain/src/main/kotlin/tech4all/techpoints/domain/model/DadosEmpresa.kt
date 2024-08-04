package tech4all.techpoints.domain.model

import java.time.LocalDateTime
import java.util.*

data class DadosEmpresa(

    var idEmpresa: Int,

    var nomeEmpresa: String,

    var setorIndustria: String,

    var cargoUsuario: String,

    var emailCorporativo: String,

    var telefoneContato: String,

    var cnpj: String,

    var dataAtualizacao: LocalDateTime?,

    var fkUsuario: Optional<Usuario>
)