package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import techForAll.techPoints.domain.TempoSessao

interface TempoSessaoRepository: JpaRepository<TempoSessao, Int> {
}