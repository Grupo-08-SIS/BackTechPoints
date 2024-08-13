package techForAll.techPoints.repository

import techForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DashboardRhRepository : JpaRepository<Usuario, Int> {
    @Query(
        """
        SELECT
            u.idUsuario, 
            u.nomeUsuario, 
            u.primeiroNome, 
            u.sobrenome, 
            u.email, 
            c.nome, 
            e.cidade
        FROM Usuario u
        JOIN u.tipoUsuario tu
        LEFT JOIN u.endereco e
        LEFT JOIN u.inscricoes i
        LEFT JOIN i.curso c
        WHERE tu.id = 2
        AND (:curso IS NULL OR c.nome = :curso)
        AND (:cidade IS NULL OR e.cidade = :cidade)
    """
    )
    fun findAlunosByCursoAndCidade(@Param("curso") curso: String?, @Param("cidade") cidade: String?): List<Any>
}