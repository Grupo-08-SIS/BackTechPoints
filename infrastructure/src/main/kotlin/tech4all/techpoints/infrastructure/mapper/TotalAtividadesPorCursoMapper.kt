package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.TotalAtividadesPorCurso
import tech4all.techpoints.infrastructure.dto.TotalAtividadesPorCursoDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TotalAtividadesPorCursoMapper {

    fun toDomain(dto: TotalAtividadesPorCursoDTO): TotalAtividadesPorCurso

    fun toDTO(domain: TotalAtividadesPorCurso): TotalAtividadesPorCursoDTO
}
