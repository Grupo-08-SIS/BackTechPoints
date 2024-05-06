package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import TechForAll.techPoints.dominio.TipoPonto
import org.springframework.data.jpa.repository.JpaRepository

interface TipoPontoRepository : JpaRepository<TipoPonto, Int> {
}