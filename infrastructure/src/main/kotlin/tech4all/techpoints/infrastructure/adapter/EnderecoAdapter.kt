package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.entity.EnderecoEntity
import tech4all.techpoints.infrastructure.repository.EnderecoRepository

@Component
class EnderecoAdapter(
    private val enderecoRepository: EnderecoRepository
) {

    fun findById(id: Int): EnderecoEntity? {
        return enderecoRepository.findById(id).orElse(null)
    }

    fun save(endereco: EnderecoEntity): EnderecoEntity {
        return enderecoRepository.save(endereco)
    }

    fun deleteById(id: Int) {
        enderecoRepository.deleteById(id)
    }

    fun findAll(): List<EnderecoEntity> {
        return enderecoRepository.findAll()
    }
}
