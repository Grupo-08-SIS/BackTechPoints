package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Classificacao
import tech4all.techpoints.infrastructure.dto.ClassificacaoDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ClassificacaoMapper {

    fun toDomain(dto: ClassificacaoDTO): Classificacao

    fun toDTO(domain: Classificacao): ClassificacaoDTO
}