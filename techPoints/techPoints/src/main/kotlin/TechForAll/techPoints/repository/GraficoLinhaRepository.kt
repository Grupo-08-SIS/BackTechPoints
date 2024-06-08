package TechForAll.techPoints.repository

import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GraficoLinhaRepository : JpaRepository<PontosAoLongoDoTempoDTO, Int> {

    @Query("""
        SELECT
            p.dataAtualizacao AS data,
            c.nome AS curso,
            SUM(p.totalPontosUsuario) AS pontos
        FROM
            Pontuacao p
        JOIN
            p.usuario u
        JOIN
            u.inscricoes i
        JOIN
            i.curso c
        WHERE
            p.usuario.idUsuario = :idUsuario
            AND YEARWEEK(p.dataAtualizacao, 1) = YEARWEEK(NOW(), 1)
        GROUP BY
            p.dataAtualizacao,
            c.nome
        ORDER BY
            p.dataAtualizacao
    """)
    fun findPontosAoLongoDoTempo(@Param("idUsuario") idUsuario: Int): List<PontosAoLongoDoTempoDTO>
}