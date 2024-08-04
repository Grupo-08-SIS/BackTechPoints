package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Curso
import tech4all.techpoints.infrastructure.entity.CursoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CursoMapper {

    fun toEntity(domain: Curso): CursoEntity

    fun toDomain(entity: CursoEntity): Curso
}