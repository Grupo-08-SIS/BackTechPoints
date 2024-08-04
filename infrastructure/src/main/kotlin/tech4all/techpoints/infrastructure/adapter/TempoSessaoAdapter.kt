package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.TempoSessaoDTO
import tech4all.techpoints.infrastructure.entity.TempoSessaoEntity
import tech4all.techpoints.infrastructure.repository.TempoSessaoRepository

@Component
class TempoSessaoAdapter(
    private val tempoSessaoRepository: TempoSessaoRepository
) {

    fun findAll(): List<TempoSessaoDTO> {
        return tempoSessaoRepository.findAll().map { entity ->
            TempoSessaoDTO(
                idTempoSessao = entity.idTempoSessao,
                diaSessao = entity.diaSessao,
                qtdTempo = entity.qtdTempo,
                idUsuario = entity.fkUsuarioEntity
            )
        }
    }

    fun findById(id: Int): TempoSessaoDTO? {
        return tempoSessaoRepository.findById(id).orElse(null)?.let { entity ->
            TempoSessaoDTO(
                idTempoSessao = entity.idTempoSessao,
                diaSessao = entity.diaSessao,
                qtdTempo = entity.qtdTempo,
                idUsuario = entity.fkUsuarioEntity
            )
        }
    }

    fun save(tempoSessaoDTO: TempoSessaoDTO): TempoSessaoDTO {
        val entity = TempoSessaoEntity(
            idTempoSessao = tempoSessaoDTO.idTempoSessao,
            diaSessao = tempoSessaoDTO.diaSessao,
            qtdTempo = tempoSessaoDTO.qtdTempo,
            fkUsuarioEntity = tempoSessaoDTO.idUsuario
        )
        val savedEntity = tempoSessaoRepository.save(entity)
        return TempoSessaoDTO(
            idTempoSessao = savedEntity.idTempoSessao,
            diaSessao = savedEntity.diaSessao,
            qtdTempo = savedEntity.qtdTempo,
            idUsuario = savedEntity.fkUsuarioEntity
        )
    }

    fun deleteById(id: Int) {
        if (tempoSessaoRepository.existsById(id)) {
            tempoSessaoRepository.deleteById(id)
        }
    }
}
