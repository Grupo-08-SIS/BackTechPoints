package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.TipoUsuario
import tech4all.techpoints.infrastructure.entity.TipoUsuarioEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface TipoUsuarioMapper {

    fun toEntity(domain: TipoUsuario): TipoUsuarioEntity

    fun toDomain(entity: TipoUsuarioEntity): TipoUsuario
}