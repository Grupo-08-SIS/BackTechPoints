package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.PontosSemana
import tech4all.techpoints.infrastructure.dto.PontosSemanaDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontosSemanaMapper {

    fun toDomain(dto: PontosSemanaDTO): PontosSemana

    fun toDTO(domain: PontosSemana): PontosSemanaDTO
}

