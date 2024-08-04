package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.PontosPorCursoAoMes
import tech4all.techpoints.infrastructure.dto.PontosPorCursoAoMesDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontosPorCursoAoMesMapper {

    fun toDomain(dto: PontosPorCursoAoMesDTO): PontosPorCursoAoMes

    fun toDTO(domain: PontosPorCursoAoMes): PontosPorCursoAoMesDTO
}
