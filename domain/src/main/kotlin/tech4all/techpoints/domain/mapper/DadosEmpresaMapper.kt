package tech4all.techpoints.domain.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.DadosEmpresa
import tech4all.techpoints.domain.dto.DadoEmpresaDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface DadosEmpresaMapper {

    fun toDto(domain: DadosEmpresa): DadoEmpresaDTO

    fun dtoToDomain(dto: DadoEmpresaDTO): DadosEmpresa
}