package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.RedefinicaoSenha
import tech4all.techpoints.infrastructure.entity.RedefinicaoSenhaEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface RedefinicaoSenhaMapper {

    fun toEntity(domain: RedefinicaoSenha): RedefinicaoSenhaEntity

    fun toDomain(entity: RedefinicaoSenhaEntity): RedefinicaoSenha
}