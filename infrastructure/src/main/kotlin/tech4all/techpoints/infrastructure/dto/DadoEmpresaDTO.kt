package tech4all.techpoints.infrastructure.dto

import java.time.LocalDateTime

data class DadoEmpresaDTO (
    var idEmpresa: Int,
    var nomeEmpresa: String,
    var setorIndustria: String,
    var cargoUsuario: String,
    var emailCorporativo: String,
    var telefoneContato: String,
    var cnpj: String,
    val dataAtualizacao: LocalDateTime?,
    var idUsuario: Int
)