package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GraficoColunaRepository : JpaRepository<Pontuacao, Int> {
    @Query("""
        SELECT
            DATE_FORMAT(p.dataAtualizacao, '%Y-%m') as mes,
            c.nome as curso,
            SUM(p.totalPontosUsuario) as pontos
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
        GROUP BY
            DATE_FORMAT(p.dataAtualizacao, '%Y-%m'),
            c.nome
        ORDER BY
            DATE_FORMAT(p.dataAtualizacao, '%Y-%m')
    """)
    fun findPontosPorCursoAoMes(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}

