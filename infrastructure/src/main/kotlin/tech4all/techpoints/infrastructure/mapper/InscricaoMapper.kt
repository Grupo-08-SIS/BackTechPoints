package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Inscricao
import tech4all.techpoints.infrastructure.entity.InscricaoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface InscricaoMapper {

    fun toEntity(domain: Inscricao): InscricaoEntity

    fun toDomain(entity: InscricaoEntity): Inscricao
}