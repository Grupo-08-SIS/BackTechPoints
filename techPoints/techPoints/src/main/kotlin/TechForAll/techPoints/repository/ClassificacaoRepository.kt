package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClassificacaoRepository : JpaRepository<Ponto, Int> {

    @Query("""
    SELECT 
        u.id_usuario AS id, 
        u.nome_usuario, 
        u.email, 
        SUM(p.qtd_ponto) AS total_pontos,
        RANK() OVER (ORDER BY SUM(p.qtd_ponto) DESC) AS ranking
    FROM 
        usuario u
    JOIN 
        ponto p ON u.id_usuario = p.fk_usuario
    JOIN 
        curso c ON p.fk_curso = c.id_curso
    WHERE 
        (:cursoId IS NULL OR c.id_curso = :cursoId)
    GROUP BY 
        u.id_usuario, u.nome_usuario, u.email
    ORDER BY 
        total_pontos DESC
    LIMIT 300
    """, nativeQuery = true)
    fun findClassificacao(cursoId: Int?): List<Array<Any>>

    @Query("""
        SELECT c.id_curso, c.nome, SUM(p.qtd_ponto)
        FROM Ponto p
        JOIN Curso c ON p.fk_curso = c.id_curso
        WHERE p.fk_usuario = :idUsuario
        GROUP BY c.id_curso, c.nome
    """, nativeQuery = true)
    fun findPontosPorCurso(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}