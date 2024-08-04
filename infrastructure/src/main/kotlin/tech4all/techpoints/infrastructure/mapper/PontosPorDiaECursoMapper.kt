package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.PontosPorDiaECurso
import tech4all.techpoints.infrastructure.dto.PontosPorDiaECursoDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontosPorDiaECursoMapper {

    fun toDomain(dto: PontosPorDiaECursoDTO): PontosPorDiaECurso

    fun toDTO(domain: PontosPorDiaECurso): PontosPorDiaECursoDTO
}
