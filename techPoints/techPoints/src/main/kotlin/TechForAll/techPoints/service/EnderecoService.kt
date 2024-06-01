package TechForAll.techPoints.service

import TechForAll.techPoints.dominio.Endereco
import TechForAll.techPoints.dto.EnderecoDTO
import TechForAll.techPoints.repository.EnderecoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.Optional

@Service
class EnderecoService {

    @Autowired
    lateinit var repository: EnderecoRepository

    fun cadastrarEndereco(novoEndereco: Endereco): Endereco {
        return repository.save(novoEndereco)
    }

    fun listarEnderecos(): List<Endereco> {
        return repository.findAll()
    }

    fun buscarEnderecoPorId(idEndereco: Int): Optional<Endereco> {
        return repository.findById(idEndereco)
    }

    fun atualizarEndereco(idEndereco: Int, enderecoDTO: EnderecoDTO): Optional<Endereco> {
        val enderecoExistente = repository.findById(idEndereco)
        if (enderecoExistente.isPresent) {
            val endereco = enderecoExistente.get()
            enderecoDTO.cep?.let { endereco.cep = it }
            enderecoDTO.cidade?.let { endereco.cidade = it }
            enderecoDTO.estado?.let { endereco.estado = it }
            enderecoDTO.rua?.let { endereco.rua = it }
            endereco.dataAtualizacao = LocalDateTime.now()
            return Optional.of(repository.save(endereco))
        }
        return Optional.empty()
    }
}
