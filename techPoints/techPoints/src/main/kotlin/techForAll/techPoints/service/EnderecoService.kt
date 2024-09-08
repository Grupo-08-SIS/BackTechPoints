package techForAll.techPoints.service

import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.repository.EnderecoRepository
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

    fun buscarEnderecoPorId(idEndereco: Long): Endereco {
        return repository.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }
    }

    fun atualizarEndereco(idEndereco: Long, enderecoAtualizado: Map<String, Any>): Endereco {
        val enderecoExistente = repository.findById(idEndereco)
            .orElseThrow { NoSuchElementException("Endereço não encontrado com o ID: $idEndereco") }

        enderecoAtualizado["cep"]?.let { enderecoExistente.cep = it as String }
        enderecoAtualizado["logradouro"]?.let { enderecoExistente.logradouro = it as String }
        enderecoAtualizado["numero"]?.let { enderecoExistente.numero = it as String }
        enderecoAtualizado["cidade"]?.let { enderecoExistente.cidade = it as String }
        enderecoAtualizado["estado"]?.let { enderecoExistente.estado = it as String }

        return repository.save(enderecoExistente)
    }
}
