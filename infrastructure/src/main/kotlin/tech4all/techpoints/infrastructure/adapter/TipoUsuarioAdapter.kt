package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.entity.TipoUsuarioEntity
import tech4all.techpoints.infrastructure.repository.TipoUsuarioRepository

@Component
class TipoUsuarioAdapter(
    private val tipoUsuarioRepository: TipoUsuarioRepository
) {

    fun findById(id: Int): TipoUsuarioEntity? {
        return tipoUsuarioRepository.findById(id).orElse(null)
    }

    fun findAll(): List<TipoUsuarioEntity> {
        return tipoUsuarioRepository.findAll()
    }

    fun save(tipoUsuarioEntity: TipoUsuarioEntity): TipoUsuarioEntity {
        return tipoUsuarioRepository.save(tipoUsuarioEntity)
    }

    fun deleteById(id: Int) {
        if (tipoUsuarioRepository.existsById(id)) {
            tipoUsuarioRepository.deleteById(id)
        }
    }
}
