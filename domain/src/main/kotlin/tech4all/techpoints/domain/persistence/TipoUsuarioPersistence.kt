package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.TipoUsuario
import java.util.Optional

interface TipoUsuarioPersistence {

    fun findAll(): List<TipoUsuario>

    fun findById(id: Int): Optional<TipoUsuario>

    fun save(tipoUsuario: TipoUsuario): TipoUsuario

    fun deleteById(id: Int)

    fun existsById(id: Int): Boolean
}
