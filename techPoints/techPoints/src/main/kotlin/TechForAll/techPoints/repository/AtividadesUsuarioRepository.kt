package TechForAll.techPoints.repository


import TechForAll.techPoints.dominio.Atividade
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AtividadesUsuarioRepository : JpaRepository<Atividade, Int> {

    @Query("""
        SELECT m.fk_curso AS id_curso, SUM(m.qtd_atividade_total) AS total_qtd_atividades
        FROM modulo m
        GROUP BY m.fk_curso
    """, nativeQuery = true)
    fun findTotalAtividadesPorCurso(): List<Array<Any>>

    @Query("""
        SELECT 
            c.id_curso,
            c.nome AS nome_curso,
            COUNT(p.fk_atividade) AS total_atividades_feitas
        FROM 
            curso c
        LEFT JOIN 
            ponto p ON c.id_curso = p.fk_curso AND p.fk_usuario = :idUsuario
        GROUP BY 
            c.id_curso, c.nome;
    """, nativeQuery = true)
    fun findAtividadesPorCursoEUsuario(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}