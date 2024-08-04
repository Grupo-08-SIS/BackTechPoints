package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Atividade
import tech4all.techpoints.infrastructure.entity.AtividadeEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface AtividadeMapper {

    fun toEntity(domain:Atividade ): AtividadeEntity;

    fun toDomain(entity: AtividadeEntity): Atividade;
}