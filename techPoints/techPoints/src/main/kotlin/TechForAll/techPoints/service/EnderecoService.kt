package TechForAll.techPoints.service

import TechForAll.techPoints.dominio.Endereco
import TechForAll.techPoints.dto.EnderecoDTO
import TechForAll.techPoints.repository.EnderecoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class EnderecoService @Autowired constructor(
    private val repository: EnderecoRepository
) {

    fun cadastrarEndereco(novoEndereco: Endereco): Endereco {
        return repository.save(novoEndereco)
    }

    fun listarEnderecos(): List<Endereco> {
        val enderecos = repository.findAll()
        if (enderecos.isEmpty()) {
            throw NoSuchElementException("Nenhum endereço encontrado")
        }
        return enderecos
    }

    fun buscarEnderecoPorId(idEndereco: Int): Endereco {
        return repository.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }
    }

    fun atualizarEndereco(idEndereco: Int, enderecoDTO: EnderecoDTO): Endereco {
        val enderecoExistente = repository.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }

        enderecoDTO.cep?.let { enderecoExistente.cep = it }
        enderecoDTO.cidade?.let { enderecoExistente.cidade = it }
        enderecoDTO.estado?.let { enderecoExistente.estado = it }
        enderecoDTO.rua?.let { enderecoExistente.rua = it }
        enderecoExistente.dataAtualizacao = LocalDateTime.now()

        return repository.save(enderecoExistente)
    }
}
