package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import TechForAll.techPoints.dto.PontosSemanaDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PontuacaoRepository : JpaRepository<Pontuacao, Int> {

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
    fun findPontosPorCursoAoMes(@Param("idUsuario") idUsuario: Int): List<PontosPorCursoAoMesDTO>



    @Query("SELECT * FROM pontuacao_usuario_semana WHERE id_usuario = :idUsuario", nativeQuery = true)
    fun findPontosSemana(idUsuario: Int): PontosSemanaDTO

}