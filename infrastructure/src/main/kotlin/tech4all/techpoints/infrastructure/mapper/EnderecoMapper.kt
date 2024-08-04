package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Endereco
import tech4all.techpoints.infrastructure.entity.EnderecoEntity

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface EnderecoMapper {

    fun toEntity(domain: Endereco): EnderecoEntity

    fun toDomain(entity: EnderecoEntity): Endereco
}