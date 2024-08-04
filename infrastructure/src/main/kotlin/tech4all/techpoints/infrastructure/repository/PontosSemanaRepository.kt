package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.dto.PontosSemanaDTO
import tech4all.techpoints.infrastructure.entity.PontoEntity

@Repository
interface PontosSemanaRepository : JpaRepository<PontoEntity, Int> {

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.PontosSemanaDTO(
            u.id,
            u.nomeUsuario,
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) THEN p.qtdPonto ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) - 1 THEN p.qtdPonto ELSE 0 END), 0),
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) THEN p.qtdPonto ELSE 0 END), 0) -
            COALESCE(SUM(CASE WHEN FUNCTION('WEEK', p.dataEntrega) = FUNCTION('WEEK', CURRENT_DATE) - 1 THEN p.qtdPonto ELSE 0 END), 0)
        )
        FROM PontoEntity p
        JOIN p.usuario u
        WHERE u.id = :idUsuario
        GROUP BY u.id, u.nomeUsuario
    """
    )
    fun findPontosSemana(@Param("idUsuario") idUsuario: Int): List<PontosSemanaDTO>
}
