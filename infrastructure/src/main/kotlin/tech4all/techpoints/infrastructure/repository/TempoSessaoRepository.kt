package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech4all.techpoints.infrastructure.entity.TempoSessaoEntity

interface TempoSessaoRepository : JpaRepository<TempoSessaoEntity, Int> {
}