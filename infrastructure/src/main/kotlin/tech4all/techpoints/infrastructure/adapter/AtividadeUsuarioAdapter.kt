package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.domain.model.Atividade
import tech4all.techpoints.infrastructure.dto.AtividadesPorCursoEUsuarioDTO
import tech4all.techpoints.infrastructure.dto.TotalAtividadesPorCursoDTO
import tech4all.techpoints.infrastructure.repository.AtividadesUsuarioRepository
import tech4all.techpoints.infrastructure.mapper.AtividadeMapper
import java.util.*

@Component
class AtividadeUsuarioAdapter(
    private val atividadesUsuarioRepository: AtividadesUsuarioRepository,
    private val atividadeMapper: AtividadeMapper
) {

    fun findTotalAtividadesPorCurso(): List<TotalAtividadesPorCursoDTO> {
        return atividadesUsuarioRepository.findTotalAtividadesPorCurso()
    }

    fun findAtividadesPorCursoEUsuario(idUsuario: Int): List<AtividadesPorCursoEUsuarioDTO> {
        return atividadesUsuarioRepository.findAtividadesPorCursoEUsuario(idUsuario)
    }

    // Métodos que utilizam AtividadeMapper para converter entidades
    fun findAll(): List<Atividade> {
        val atividadeUsuarioEntities = atividadesUsuarioRepository.findAll()
        return atividadeUsuarioEntities.map(atividadeMapper::toDomain)
    }

    fun findById(id: Int): Optional<Atividade> {
        val atividadeUsuarioEntity = atividadesUsuarioRepository.findById(id)
        return atividadeUsuarioEntity.map(atividadeMapper::toDomain)
    }

    fun existsById(id: Int): Boolean {
        return atividadesUsuarioRepository.existsById(id)
    }

    fun create(atividadeUsuario: Atividade): Atividade {
        val atividadeUsuarioEntity = atividadeMapper.toEntity(atividadeUsuario)
        val atividadeUsuarioEntitySaved = atividadesUsuarioRepository.save(atividadeUsuarioEntity)
        return atividadeMapper.toDomain(atividadeUsuarioEntitySaved)
    }

    fun update(atividadeUsuario: Atividade): Atividade {
        val atividadeUsuarioEntity = atividadeMapper.toEntity(atividadeUsuario)
        val atividadeUsuarioEntityUpdated = atividadesUsuarioRepository.save(atividadeUsuarioEntity)
        return atividadeMapper.toDomain(atividadeUsuarioEntityUpdated)
    }
}
