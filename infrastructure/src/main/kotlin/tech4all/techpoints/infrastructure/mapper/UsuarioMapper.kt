package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Usuario
import tech4all.techpoints.infrastructure.entity.UsuarioEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UsuarioMapper {

    fun toEntity(domain: Usuario): UsuarioEntity

    fun toDomain(entity: UsuarioEntity): Usuario
}