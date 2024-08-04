package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.dto.DadoEmpresaDTO
import tech4all.techpoints.domain.dto.DadosEmpresaDTOAtt
import tech4all.techpoints.domain.model.DadosEmpresa
import java.util.Optional

interface DadoEmpresaService {

    fun findAll(): List<DadoEmpresaDTO>

    fun findById(id: Int): DadoEmpresaDTO

    fun create(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO

    fun update(id: Int, dadoEmpresaDTO: DadosEmpresaDTOAtt): DadoEmpresaDTO

    fun cadastrarEmpresa(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO

    fun obterEmpresaPorId(id: Int): DadoEmpresaDTO

    fun atualizarEmpresa(id: Int, dadoEmpresaDTO: DadosEmpresaDTOAtt): DadoEmpresaDTO
}
