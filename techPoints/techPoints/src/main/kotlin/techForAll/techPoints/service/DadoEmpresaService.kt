package techForAll.techPoints.service

import techForAll.techPoints.domain.DadosEmpresa
import techForAll.techPoints.dto.DadoEmpresaDTO
import techForAll.techPoints.dto.DadosEmpresaDTOAtt
import techForAll.techPoints.repository.DadosEmpresaRepository
import techForAll.techPoints.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.NoSuchElementException

@Service
class DadoEmpresaService @Autowired constructor(
    private val dadoEmpresaRepository: DadosEmpresaRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun cadastrarEmpresa(dadoEmpresaDTO: DadoEmpresaDTO): DadoEmpresaDTO {
        val usuario = usuarioRepository.findById(dadoEmpresaDTO.idUsuario)
            .orElseThrow { IllegalArgumentException("Usuario não encontrado com o ID: ${dadoEmpresaDTO.idUsuario}") }

        val dadoEmpresa = DadosEmpresa(
            idEmpresa = dadoEmpresaDTO.idEmpresa,
            nomeEmpresa = dadoEmpresaDTO.nomeEmpresa,
            setorIndustria = dadoEmpresaDTO.setorIndustria,
            cargoUsuario = dadoEmpresaDTO.cargoUsuario,
            emailCorporativo = dadoEmpresaDTO.emailCorporativo,
            telefoneContato = dadoEmpresaDTO.telefoneContato,
            cnpj = dadoEmpresaDTO.cnpj,
            dataAtualizacao = null,
            fkUsuario = usuario!!
        )

        val empresaSalva = dadoEmpresaRepository.save(dadoEmpresa)
        return dadoEmpresaParaDTO(empresaSalva)
    }

    fun obterEmpresaPorId(id: Int): DadoEmpresaDTO {
        val dadosEmpresa = dadoEmpresaRepository.findById(id)
            .orElseThrow { NoSuchElementException("Empresa não encontrada com o ID: $id") }
        return dadoEmpresaParaDTO(dadosEmpresa)
    }

    fun atualizarEmpresa(id: Int, dadoEmpresaDTO: DadosEmpresaDTOAtt): DadoEmpresaDTO {
        val empresaExistente = dadoEmpresaRepository.findById(id)
            .orElseThrow { NoSuchElementException("Empresa não encontrada com o ID: $id") }

        dadoEmpresaDTO.nomeEmpresa?.let { empresaExistente.nomeEmpresa = it }
        dadoEmpresaDTO.setorIndustria?.let { empresaExistente.setorIndustria = it }
        dadoEmpresaDTO.cargoUsuario?.let { empresaExistente.cargoUsuario = it }
        dadoEmpresaDTO.emailCorporativo?.let { empresaExistente.emailCorporativo = it }
        dadoEmpresaDTO.telefoneContato?.let { empresaExistente.telefoneContato = it }
        dadoEmpresaDTO.cnpj?.let { empresaExistente.cnpj = it }
        empresaExistente.dataAtualizacao = LocalDateTime.now()

        val empresaAtualizada = dadoEmpresaRepository.save(empresaExistente)
        return dadoEmpresaParaDTO(empresaAtualizada)
    }

    private fun dadoEmpresaParaDTO(dadoEmpresa: DadosEmpresa): DadoEmpresaDTO {
        return DadoEmpresaDTO(
            idEmpresa = dadoEmpresa.idEmpresa,
            nomeEmpresa = dadoEmpresa.nomeEmpresa,
            setorIndustria = dadoEmpresa.setorIndustria,
            cargoUsuario = dadoEmpresa.cargoUsuario,
            emailCorporativo = dadoEmpresa.emailCorporativo,
            telefoneContato = dadoEmpresa.telefoneContato,
            cnpj = dadoEmpresa.cnpj,
            dataAtualizacao = dadoEmpresa.dataAtualizacao,
            idUsuario = dadoEmpresa.fkUsuario.idUsuario
        )
    }
}
