package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.ReferenciaGerarPontuacao
import tech4all.techpoints.infrastructure.entity.ReferenciaGerarPontuacaoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface ReferenciaGerarPontuacaoMapper {

    fun toEntity(domain: ReferenciaGerarPontuacao): ReferenciaGerarPontuacaoEntity

    fun toDomain(entity: ReferenciaGerarPontuacaoEntity): ReferenciaGerarPontuacao
}