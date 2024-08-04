package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.dto.PontosPorDiaECursoDTO
import tech4all.techpoints.infrastructure.entity.PontoEntity

@Repository
interface GraficoLinhaRepository : JpaRepository<PontoEntity, Int> {

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.PontosPorDiaECursoDTO(
            p.usuarioId,
            p.cursoId,
            p.nomeCurso,
            p.data,
            p.pontos
        )
        FROM PontosPorDiaECursoEntity p
        WHERE p.usuarioId = :idUsuario
    """
    )
    fun findPontosPorDiaECurso(@Param("idUsuario") idUsuario: Int): List<PontosPorDiaECursoDTO>
}
