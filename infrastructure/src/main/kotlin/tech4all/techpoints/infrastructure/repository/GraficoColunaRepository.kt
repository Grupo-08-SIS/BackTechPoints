package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.dto.PontosPorCursoAoMesDTO
import tech4all.techpoints.infrastructure.entity.PontoEntity

@Repository
interface GraficoColunaRepository : JpaRepository<PontoEntity, Int> {

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.PontosPorCursoAoMesDTO(
            FUNCTION('MONTH', p.dataEntrega),
            c.id,
            c.nome,
            SUM(p.qtdPonto)
        )
        FROM PontoEntity p
        JOIN p.curso c
        WHERE p.usuario.id = :idUsuario
        GROUP BY FUNCTION('MONTH', p.dataEntrega), c.id, c.nome
        ORDER BY FUNCTION('MONTH', p.dataEntrega), c.id
    """
    )
    fun findPontosPorCursoAoMes(@Param("idUsuario") idUsuario: Int): List<PontosPorCursoAoMesDTO>
}
