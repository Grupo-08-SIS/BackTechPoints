package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Ponto
import TechForAll.techPoints.dominio.Pontuacao
import org.springframework.data.jpa.repository.JpaRepository

interface PontoRepository: JpaRepository<Ponto, Int> {

}