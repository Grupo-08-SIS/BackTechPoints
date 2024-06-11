package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GraficoColunaRepository : JpaRepository<Ponto, Int> {
    @Query("""
        SELECT 
            MONTH(p.data_entrega) AS mes,
            c.id_curso,
            c.nome AS nome_curso,
            SUM(p.qtd_ponto) AS total_pontos
        FROM 
            ponto p
        INNER JOIN 
            curso c ON p.fk_curso = c.id_curso
        WHERE 
            p.fk_usuario = :idUsuario
        GROUP BY 
            mes, c.id_curso, c.nome
        ORDER BY 
            mes, c.id_curso;
    """, nativeQuery = true)
    fun findPontosPorCursoAoMes(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}

