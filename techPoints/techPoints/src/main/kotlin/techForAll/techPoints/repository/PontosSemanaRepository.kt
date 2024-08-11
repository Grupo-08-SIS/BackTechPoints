package techForAll.techPoints.repository

import techForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PontosSemanaRepository : JpaRepository<Ponto, Int>{

    @Query(
        """
        SELECT 
            u.idUsuario,
            u.nomeUsuario,
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) THEN p.qtdPonto ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) - 1 THEN p.qtdPonto ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) THEN p.qtdPonto ELSE 0 END), 0) -
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) - 1 THEN p.qtdPonto ELSE 0 END), 0)
        FROM Ponto p
        JOIN p.usuario u
        WHERE u.idUsuario = :idUsuario
        GROUP BY u.idUsuario, u.nomeUsuario
    """
    )
    fun findPontosSemana(@Param("idUsuario") idUsuario: Int): List<Array<Any>>

}