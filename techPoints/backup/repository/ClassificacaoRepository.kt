package techForAll.techPoints.repository

import techForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClassificacaoRepository : JpaRepository<Ponto, Int> {

    @Query(
        """
        SELECT
            u.idUsuario, 
            u.nomeUsuario, 
            u.email, 
            SUM(p.qtdPonto), 
            RANK() OVER (ORDER BY SUM(p.qtdPonto) DESC)
        FROM Usuario u
        JOIN Ponto p ON p.usuario.idUsuario = u.idUsuario
        JOIN u.inscricoes.curso c
        WHERE (:cursoId IS NULL OR c.idCurso = :cursoId)
        GROUP BY u.idUsuario, u.nomeUsuario, u.email
        ORDER BY SUM(p.qtdPonto) DESC
    """
    )
    fun findClassificacao(cursoId: Int?): List<Array<Any>>

    @Query(
        """
        SELECT
            c.idCurso, 
            c.nome, 
            SUM(p.qtdPonto)
        FROM Ponto p
        JOIN p.usuario.inscricoes.curso c
        WHERE p.usuario.idUsuario = :idUsuario
        GROUP BY c.idCurso, c.nome
    """
    )
    fun findPontosPorCurso(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
}