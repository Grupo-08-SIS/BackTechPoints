package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Ponto
import tech4all.techpoints.infrastructure.entity.PontoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontoMapper {

    fun toEntity(domain:Ponto): PontoEntity

    fun toDomain(entity: PontoEntity): Ponto
}