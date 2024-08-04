package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech4all.techpoints.infrastructure.entity.EnderecoEntity

interface EnderecoRepository : JpaRepository<EnderecoEntity, Int> {
}