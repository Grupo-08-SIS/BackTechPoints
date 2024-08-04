package tech4all.techpoints.domain.service.impl

import org.springframework.stereotype.Service
import tech4all.techpoints.domain.model.DadosEmpresa
import tech4all.techpoints.domain.persistence.DadoEmpresaPersistence
import tech4all.techpoints.domain.persistence.UsuarioPersistence
import tech4all.techpoints.domain.service.DadoEmpresaService
import tech4all.techpoints.domain.dto.DadoEmpresaDTO
import tech4all.techpoints.domain.dto.DadosEmpresaDTOAtt
import tech4all.techpoints.domain.mapper.DadosEmpresaMapper
import java.time.LocalDateTime

@Service
class DadoEmpresaServiceImpl(
    private val dadoEmpresaPersistence: DadoEmpresaPersistence,
    private val usuarioPersistence: UsuarioPersistence,
    private val dadosEmpresaMapper: DadosEmpresaMapper
) : DadoEmpresaService {

    override fun cadastrarEmpresa(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO {
        val usuario = usuarioPersistence.findById(dadoEmpresaDTO.idUsuario)
            ?: throw IllegalArgumentException("Usuário não encontrado com o ID: ${dadoEmpresaDTO.idUsuario}")

        val dadoEmpresa = DadosEmpresa(
            idEmpresa = dadoEmpresaDTO.idEmpresa,
            nomeEmpresa = dadoEmpresaDTO.nomeEmpresa,
            setorIndustria = dadoEmpresaDTO.setorIndustria,
            cargoUsuario = dadoEmpresaDTO.cargoUsuario,
            emailCorporativo = dadoEmpresaDTO.emailCorporativo,
            telefoneContato = dadoEmpresaDTO.telefoneContato,
            cnpj = dadoEmpresaDTO.cnpj,
            dataAtualizacao = null,
            fkUsuario = usuario
        )

        val empresaSalva = dadoEmpresaPersistence.create(dadoEmpresa)
        return dadosEmpresaMapper.toDto(empresaSalva)
    }

    override fun obterEmpresaPorId(id: Int): DadoEmpresaDTO {
        val empresa = dadoEmpresaPersistence.findById(id)
            ?: throw NoSuchElementException("Empresa não encontrada com o ID: $id")
        return dadosEmpresaMapper.toDto(empresa)
    }

    override fun atualizarEmpresa(id: Int, dadoEmpresaDTO: DadosEmpresaDTOAtt): DadoEmpresaDTO {
        val empresaExistente = dadoEmpresaPersistence.findById(id)
            ?: throw NoSuchElementException("Empresa não encontrada com o ID: $id")

        dadoEmpresaDTO.nomeEmpresa?.let { empresaExistente.nomeEmpresa = it }
        dadoEmpresaDTO.setorIndustria?.let { empresaExistente.setorIndustria = it }
        dadoEmpresaDTO.cargoUsuario?.let { empresaExistente.cargoUsuario = it }
        dadoEmpresaDTO.emailCorporativo?.let { empresaExistente.emailCorporativo = it }
        dadoEmpresaDTO.telefoneContato?.let { empresaExistente.telefoneContato = it }
        dadoEmpresaDTO.cnpj?.let { empresaExistente.cnpj = it }
        empresaExistente.dataAtualizacao = LocalDateTime.now()

        val empresaAtualizada = dadoEmpresaPersistence.update(id, empresaExistente)
        return dadosEmpresaMapper.toDto(empresaAtualizada)
    }

    override fun findAll(): List<DadoEmpresaDTO> {
        return dadoEmpresaPersistence.findAll().map(dadosEmpresaMapper::toDto)
    }

    override fun findById(id: Int): DadoEmpresaDTO {
        val dadosEmpresa = dadoEmpresaPersistence.findById(id)
            ?: throw NoSuchElementException("Empresa não encontrada com o ID: $id")
        return dadosEmpresaMapper.toDto(dadosEmpresa)
    }

    override fun create(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO {
        val dadoEmpresa = dadosEmpresaMapper.dtoToDomain(dadoEmpresaDTO)
        val empresaSalva = dadoEmpresaPersistence.create(dadoEmpresa)
        return dadosEmpresaMapper.toDto(empresaSalva)
    }

    override fun update(id: Int, dadoEmpresaDTO: DadosEmpresaDTOAtt): DadoEmpresaDTO {
        val empresaExistente = dadoEmpresaPersistence.findById(id)
            ?: throw NoSuchElementException("Empresa não encontrada com o ID: $id")

        dadoEmpresaDTO.nomeEmpresa?.let { empresaExistente.nomeEmpresa = it }
        dadoEmpresaDTO.setorIndustria?.let { empresaExistente.setorIndustria = it }
        dadoEmpresaDTO.cargoUsuario?.let { empresaExistente.cargoUsuario = it }
        dadoEmpresaDTO.emailCorporativo?.let { empresaExistente.emailCorporativo = it }
        dadoEmpresaDTO.telefoneContato?.let { empresaExistente.telefoneContato = it }
        dadoEmpresaDTO.cnpj?.let { empresaExistente.cnpj = it }
        empresaExistente.dataAtualizacao = LocalDateTime.now()

        val empresaAtualizada = dadoEmpresaPersistence.update(id, empresaExistente)
        return dadosEmpresaMapper.toDto(empresaAtualizada)
    }
}
