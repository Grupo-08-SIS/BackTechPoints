package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import TechForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface PontuacaoRepository : JpaRepository<Pontuacao, Int> {

}