package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.dto.ClassificacaoDTO
import tech4all.techpoints.infrastructure.dto.PontosPorCursoDTO
import tech4all.techpoints.infrastructure.entity.PontoEntity

@Repository
interface ClassificacaoRepository : JpaRepository<PontoEntity, Int>, JpaSpecificationExecutor<PontoEntity> {

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.ClassificacaoDTO(
            u.id, 
            u.nomeUsuario, 
            u.email, 
            SUM(p.qtdPonto), 
            RANK() OVER (ORDER BY SUM(p.qtdPonto) DESC)
        )
        FROM UsuarioEntity u
        JOIN u.pontos p
        JOIN p.curso c
        WHERE (:cursoId IS NULL OR c.id = :cursoId)
        GROUP BY u.id, u.nomeUsuario, u.email
        ORDER BY SUM(p.qtdPonto) DESC
    """
    )
    fun findClassificacao(@Param("cursoId") cursoId: Int?): List<ClassificacaoDTO>

    @Query(
        """
        SELECT new tech4all.techpoints.infrastructure.dto.PontosPorCursoDTO(
            c.id, 
            c.nome, 
            SUM(p.qtdPonto)
        )
        FROM PontoEntity p
        JOIN p.curso c
        WHERE p.usuario.id = :idUsuario
        GROUP BY c.id, c.nome
    """
    )
    fun findPontosPorCurso(@Param("idUsuario") idUsuario: Int): List<PontosPorCursoDTO>
}
