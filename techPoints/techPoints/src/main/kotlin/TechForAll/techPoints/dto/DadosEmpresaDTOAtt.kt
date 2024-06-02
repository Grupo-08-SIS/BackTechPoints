package TechForAll.techPoints.dto

import java.time.LocalDateTime

data class DadosEmpresaDTOAtt(
    val idEmpresa: Int?,
    val nomeEmpresa: String?,
    val setorIndustria: String?,
    val cargoUsuario: String?,
    val emailCorporativo: String?,
    val telefoneContato: String?,
    val cnpj: String?,
    val dataAtualizacao: LocalDateTime?,
    val idUsuario: Int
)