package techForAll.techPoints.service

import techForAll.techPoints.domain.DadosEmpresa

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import techForAll.techPoints.dtos.EmpresaComRecrutadoresDTO
import techForAll.techPoints.dtos.EmpresaInput
import techForAll.techPoints.repository.DadosEmpresaRepository
import techForAll.techPoints.repository.EnderecoRepository
import java.time.LocalDateTime
import java.util.NoSuchElementException

@Service
class DadoEmpresaService @Autowired constructor(
    private val empresaRepository: DadosEmpresaRepository,
    private val enderecoRepository: EnderecoRepository
) {
    fun cadastrarEmpresa(novaEmpresa: EmpresaInput): DadosEmpresa {
        val endereco = enderecoRepository.findById(novaEmpresa.enderecoId)
            .orElseThrow { IllegalArgumentException("Endereço não encontrado") }
        val dadosEmpresa = DadosEmpresa(
            nomeEmpresa = novaEmpresa.nomeEmpresa,
            cnpj = novaEmpresa.cnpj,
            setorIndustria = novaEmpresa.setorIndustria,
            telefoneContato = novaEmpresa.telefoneContato,
            emailCorporativo = novaEmpresa.emailCorporativo,
            endereco = endereco
        )
        return empresaRepository.save(dadosEmpresa)
    }

    fun listarEmpresas(): List<DadosEmpresa> {
        val empresas = empresaRepository.findAll()
        if (empresas.isEmpty()) {
            throw NoSuchElementException("Nenhuma empresa encontrada")
        }
        return empresas
    }

    fun buscarEmpresaPorId(idEmpresa: Long): EmpresaComRecrutadoresDTO {
        val dadosEmpresa = empresaRepository.findById(idEmpresa)
            .orElseThrow { NoSuchElementException("Empresa não encontrada com o ID: $idEmpresa") }

        val dadosEmpresaExibir = EmpresaComRecrutadoresDTO(
            id = dadosEmpresa.id,
            nomeEmpresa = dadosEmpresa.nomeEmpresa,
            cnpj = dadosEmpresa.cnpj,
            setorIndustria = dadosEmpresa.setorIndustria,
            telefoneContato = dadosEmpresa.telefoneContato,
            emailCorporativo = dadosEmpresa.emailCorporativo,
            endereco = dadosEmpresa.endereco,
            dataCriacao = dadosEmpresa.dataCriacao,
            recrutadores = dadosEmpresa.recrutadores.map{ it.nomeUsuario }
        )

        return dadosEmpresaExibir
    }

    fun atualizarEmpresa(idEmpresa: Long, empresaAtualizada: Map<String, Any>): DadosEmpresa {
        val empresaExistente = empresaRepository.findById(idEmpresa)
            .orElseThrow { NoSuchElementException("Empresa não encontrada com o ID: $idEmpresa") }

        empresaAtualizada["nomeEmpresa"]?.let { empresaExistente.nomeEmpresa = it as String }
        empresaAtualizada["setorIndustria"]?.let { empresaExistente.setorIndustria = it as String }
        empresaAtualizada["telefoneContato"]?.let { empresaExistente.telefoneContato = it as String }
        empresaAtualizada["emailCorporativo"]?.let { empresaExistente.emailCorporativo = it as String }
        empresaAtualizada["cnpj"]?.let { empresaExistente.cnpj = it as String }
        empresaExistente.dataAtualizacao = LocalDateTime.now()

        return empresaRepository.save(empresaExistente)
    }

    fun deletarEmpresa(idEmpresa: Long) {
        val empresaExistente = empresaRepository.findById(idEmpresa)
            .orElseThrow { NoSuchElementException("Empresa não encontrada com o ID: $idEmpresa") }

        empresaRepository.delete(empresaExistente)
    }

}
