package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import techForAll.techPoints.domain.TempoEstudo

interface TempoEstudoRepository: JpaRepository<TempoEstudo, Int> {
}