package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.TempoEstudo
import tech4all.techpoints.infrastructure.entity.TempoEstudoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TempoEstudoMapper {

    fun toEntity(domain: TempoEstudo): TempoEstudoEntity

    fun toDomain(entity: TempoEstudoEntity): TempoEstudo
}