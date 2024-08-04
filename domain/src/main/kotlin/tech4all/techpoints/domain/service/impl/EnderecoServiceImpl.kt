package tech4all.techpoints.domain.service.impl

import org.springframework.stereotype.Service
import tech4all.techpoints.domain.model.Endereco
import tech4all.techpoints.domain.service.EnderecoService
import tech4all.techpoints.domain.persistence.EnderecoPersistence
import tech4all.techpoints.domain.dto.EnderecoDTO
import java.time.LocalDateTime
import java.util.NoSuchElementException

@Service
class EnderecoServiceImpl(
    private val enderecoPersistence: EnderecoPersistence
) : EnderecoService {

    override fun cadastrarEndereco(novoEndereco: Endereco): Endereco {
        return enderecoPersistence.save(novoEndereco)
    }

    override fun listarEnderecos(): List<Endereco> {
        val enderecos = enderecoPersistence.findAll()
        if (enderecos.isEmpty()) {
            throw NoSuchElementException("Nenhum endereço encontrado")
        }
        return enderecos
    }

    override fun buscarEnderecoPorId(idEndereco: Int): Endereco {
        return enderecoPersistence.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }
    }

    override fun atualizarEndereco(idEndereco: Int, enderecoDTO: EnderecoDTO): Endereco {
        val enderecoExistente = enderecoPersistence.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }

        enderecoDTO.cep?.let { enderecoExistente.cep = it }
        enderecoDTO.cidade?.let { enderecoExistente.cidade = it }
        enderecoDTO.estado?.let { enderecoExistente.estado = it }
        enderecoDTO.rua?.let { enderecoExistente.rua = it }
        enderecoExistente.dataAtualizacao = LocalDateTime.now()

        return enderecoPersistence.save(enderecoExistente)
    }
}
