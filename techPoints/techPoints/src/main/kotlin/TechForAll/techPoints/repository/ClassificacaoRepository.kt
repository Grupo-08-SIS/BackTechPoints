package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ClassificacaoRepository : JpaRepository<Ponto, Int> {

    @Query("""
        SELECT u.id, u.nome, u.email, SUM(p.qtd_ponto) as total_pontos
        FROM Usuario u
        JOIN Ponto p ON u.id = p.fkUsuario
        JOIN Curso c ON p.fkCurso = c.id
        WHERE (:cursoId IS NULL OR c.id = :cursoId)
        GROUP BY u.id, u.nome, u.email
        ORDER BY total_pontos DESC
    """, nativeQuery = true)
    fun findClassificacao(cursoId: Int?): List<Array<Any>>
}