package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.DadosEmpresa

interface DadoEmpresaPersistence {

    fun findAll(): List<DadosEmpresa>

    fun create(dadoEmpresa: DadosEmpresa): DadosEmpresa

    fun findById(id: Int): DadosEmpresa?

    fun update(id: Int, dadoEmpresa: DadosEmpresa): DadosEmpresa
}
