package tech4all.techpoints.infrastructure.mapper

import org.mapstruct.Mapper
import org.mapstruct.MappingConstants
import tech4all.techpoints.domain.model.Aluno
import tech4all.techpoints.infrastructure.dto.AlunoDTO

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
interface AlunoMapper {

    fun toDomain(dto: AlunoDTO): Aluno

    fun toDTO(domain: Aluno): AlunoDTO
}
