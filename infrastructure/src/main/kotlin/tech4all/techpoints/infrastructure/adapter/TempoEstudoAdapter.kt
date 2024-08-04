package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.entity.TempoEstudoEntity
import tech4all.techpoints.infrastructure.repository.TempoEstudoRepository
import tech4all.techpoints.infrastructure.dto.TempoEstudoDTO
import java.util.Optional

@Component
class TempoEstudoAdapter(
    private val tempoEstudoRepository: TempoEstudoRepository
) {

    fun findAll(): List<TempoEstudoDTO> {
        val entities = tempoEstudoRepository.findAll()
        return entities.map { entity ->
            TempoEstudoDTO(
                id = entity.idTempoEstudo,
                nomeDia = entity.nomeDia,
                qtdTempoEstudo = entity.qtdTempoEstudo,
                metaAtingida = entity.metaAtingida
            )
        }
    }

    fun findById(id: Int): Optional<TempoEstudoDTO> {
        val entity = tempoEstudoRepository.findById(id)
        return entity.map {
            TempoEstudoDTO(
                id = it.idTempoEstudo,
                nomeDia = it.nomeDia,
                qtdTempoEstudo = it.qtdTempoEstudo,
                metaAtingida = it.metaAtingida
            )
        }
    }

    fun save(tempoEstudoDTO: TempoEstudoDTO): TempoEstudoDTO {
        val entity = TempoEstudoEntity(
            idTempoEstudo = tempoEstudoDTO.id,
            nomeDia = tempoEstudoDTO.nomeDia,
            qtdTempoEstudo = tempoEstudoDTO.qtdTempoEstudo,
            ativado = true,
            metaAtingida = tempoEstudoDTO.metaAtingida

        )
        val savedEntity = tempoEstudoRepository.save(entity)
        return TempoEstudoDTO(
            id = savedEntity.idTempoEstudo,
            nomeDia = savedEntity.nomeDia,
            qtdTempoEstudo = savedEntity.qtdTempoEstudo,
            metaAtingida = savedEntity.metaAtingida

        )
    }

    fun deleteById(id: Int) {
        tempoEstudoRepository.deleteById(id)
    }
}
