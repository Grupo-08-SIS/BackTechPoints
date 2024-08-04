package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.DadosEmpresa
import tech4all.techpoints.infrastructure.dto.DadoEmpresaDTO
import tech4all.techpoints.infrastructure.entity.DadosEmpresaEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface DadosEmpresaMapper {

    fun toEntity(domain: DadosEmpresa): DadosEmpresaEntity

    fun toDomain(entity: DadosEmpresaEntity): DadosEmpresa

    fun toDto(domain: DadosEmpresa): DadoEmpresaDTO

    fun dtoToDomain(dto: DadoEmpresaDTO): DadosEmpresa
}