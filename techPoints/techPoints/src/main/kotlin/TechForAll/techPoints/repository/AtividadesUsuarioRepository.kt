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
            m.fk_curso AS id_curso,
            c.nome AS nome_curso,
            COUNT(DISTINCT a.id_atividade) AS total_atividades_usuario
        FROM modulo m
        INNER JOIN curso c ON m.fk_curso = c.id_curso
        LEFT JOIN atividade a ON m.id_modulo = a.fk_modulo AND a.fk_curso = m.fk_curso
        LEFT JOIN ponto p ON a.id_atividade = p.fk_atividade AND p.fk_usuario = :idUsuario
        WHERE m.fk_curso IN (SELECT i.fk_curso FROM inscricao i WHERE i.fk_usuario = :idUsuario)
        GROUP BY m.fk_curso, c.nome
    """, nativeQuery = true)
    fun findAtividadesPorCursoEUsuario(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}