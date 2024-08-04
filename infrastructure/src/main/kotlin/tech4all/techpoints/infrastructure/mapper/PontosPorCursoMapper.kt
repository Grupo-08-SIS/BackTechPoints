package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.PontosPorCurso
import tech4all.techpoints.infrastructure.dto.PontosPorCursoDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontosPorCursoMapper {

    fun toDomain(dto: PontosPorCursoDTO): PontosPorCurso

    fun toDTO(domain: PontosPorCurso): PontosPorCursoDTO
}