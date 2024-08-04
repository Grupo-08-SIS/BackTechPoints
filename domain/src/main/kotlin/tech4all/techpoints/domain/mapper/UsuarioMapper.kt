package tech4all.techpoints.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Usuario
import tech4all.techpoints.domain.dto.UsuarioDTOInput
import tech4all.techpoints.domain.dto.UsuarioDTOOutput

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface UsuarioMapper {

    fun toDomain(dto: UsuarioDTOInput): Usuario

    fun toDTO(domain: Usuario): UsuarioDTOOutput

    fun toDtoInput(usuario: UsuarioDTOOutput): UsuarioDTOInput
}
