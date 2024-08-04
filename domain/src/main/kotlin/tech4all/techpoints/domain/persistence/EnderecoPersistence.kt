package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.Endereco
import java.util.Optional

interface EnderecoPersistence {

    fun save(endereco: Endereco): Endereco

    fun findAll(): List<Endereco>

    fun findById(idEndereco: Int): Optional<Endereco>
}
