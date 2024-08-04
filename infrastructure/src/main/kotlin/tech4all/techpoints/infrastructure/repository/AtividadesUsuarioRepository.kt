package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.dto.AtividadesPorCursoEUsuarioDTO
import tech4all.techpoints.infrastructure.dto.TotalAtividadesPorCursoDTO
import tech4all.techpoints.infrastructure.entity.AtividadeEntity

@Repository
interface AtividadesUsuarioRepository : JpaRepository<AtividadeEntity, Int>, JpaSpecificationExecutor<AtividadeEntity> {

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.TotalAtividadesPorCursoDTO(m.fkCurso, SUM(m.qtdAtividadeTotal))
        FROM ModuloEntity m
        GROUP BY m.fkCurso
    """
    )
    fun findTotalAtividadesPorCurso(): List<TotalAtividadesPorCursoDTO>

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.AtividadesPorCursoEUsuarioDTO(
            c.idCurso,
            c.nome,
            COUNT(p.fkAtividade)
        )
        FROM CursoEntity c
        LEFT JOIN PontoEntity p ON c.idCurso = p.fkCurso AND p.fkUsuario = :idUsuario
        GROUP BY c.idCurso, c.nome
    """
    )
    fun findAtividadesPorCursoEUsuario(@Param("idUsuario") idUsuario: Int): List<AtividadesPorCursoEUsuarioDTO>
}