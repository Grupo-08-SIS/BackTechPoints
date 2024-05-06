package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PontuacaoRepository : JpaRepository<Pontuacao, Int> {
    @Query("""
        SELECT p.totalPontosUsuario
        FROM Pontuacao p
        WHERE p.idPontuacao = :pontuacaoId
    """)
    fun obterTotalDePontos(pontuacaoId: Int): Int?
}