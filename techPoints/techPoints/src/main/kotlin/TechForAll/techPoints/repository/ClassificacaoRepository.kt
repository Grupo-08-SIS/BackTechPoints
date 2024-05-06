package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Classificacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ClassificacaoRepository : JpaRepository<Classificacao, Int> {

    @Query("""
        SELECT 
            c.usuario.idUsuario as usuarioId,
            c.usuario.nomeUsuario as nomeUsuario,
            SUM(p.totalPontosUsuario) as totalPontos
        FROM 
            Classificacao c
        JOIN 
            c.pontuacao p
        GROUP BY 
            c.usuario.nomeUsuario
        ORDER BY 
            totalPontos DESC
    """)
    fun buscarRankingGeral(): List<Classificacao>

}