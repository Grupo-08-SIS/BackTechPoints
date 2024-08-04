package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.TempoSessao
import tech4all.techpoints.infrastructure.entity.TempoSessaoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TempoSessaoMapper {

    fun toEntity(domain: TempoSessao): TempoSessaoEntity

    fun toDomain(entity: TempoSessaoEntity): TempoSessao
}