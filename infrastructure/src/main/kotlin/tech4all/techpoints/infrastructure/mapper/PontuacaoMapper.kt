package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Pontuacao
import tech4all.techpoints.infrastructure.entity.PontuacaoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface PontuacaoMapper {

    fun toEntity(domain: Pontuacao): PontuacaoEntity

    fun toDomain(entity: PontuacaoEntity): Pontuacao
}