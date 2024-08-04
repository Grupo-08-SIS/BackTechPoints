package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Modulo
import tech4all.techpoints.infrastructure.entity.ModuloEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ModuloMapper {

    fun toEntity(domain: Modulo): ModuloEntity

    fun toDomain(entity: ModuloEntity): Modulo
}