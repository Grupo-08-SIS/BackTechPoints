package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.AtividadesPorCursoEUsuario
import tech4all.techpoints.infrastructure.dto.AtividadesPorCursoEUsuarioDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface AtividadesPorCursoEUsuarioMapper {

    fun toDomain(dto: AtividadesPorCursoEUsuarioDTO): AtividadesPorCursoEUsuario

    fun toDTO(domain: AtividadesPorCursoEUsuario): AtividadesPorCursoEUsuarioDTO
}
