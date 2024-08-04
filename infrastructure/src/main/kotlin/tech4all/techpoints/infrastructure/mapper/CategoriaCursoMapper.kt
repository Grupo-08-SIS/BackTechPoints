package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.CategoriaCurso
import tech4all.techpoints.infrastructure.entity.CategoriaCursoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface CategoriaCursoMapper {

    fun toEntity(domain: CategoriaCurso): CategoriaCursoEntity

    fun toDomain(entity: CategoriaCursoEntity): CategoriaCurso
}