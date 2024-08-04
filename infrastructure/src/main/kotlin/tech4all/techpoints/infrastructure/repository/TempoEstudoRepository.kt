package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech4all.techpoints.infrastructure.entity.TempoEstudoEntity

interface TempoEstudoRepository : JpaRepository<TempoEstudoEntity, Int> {
}