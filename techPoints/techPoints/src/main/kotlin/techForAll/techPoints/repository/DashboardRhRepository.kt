package techForAll.techPoints.repository

import techForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import techForAll.techPoints.dominio.Curso
import techForAll.techPoints.dto.AlunoDTO
import techForAll.techPoints.dto.AlunosPorCursoDTO
import techForAll.techPoints.dto.CursoComAlunoDTO
import techForAll.techPoints.dto.RhAlunoCursoDto
import java.util.Optional

@Repository
interface DashboardRhRepository : JpaRepository<Curso, Int> {

    @Query(
        """
        SELECT new techForAll.techPoints.dto.CursoComAlunoDTO(
            c.idCurso, 
            c.nome, 
            u.idUsuario, 
            u.nomeUsuario
        )
        FROM Curso c
        LEFT JOIN c.inscricoes i
        LEFT JOIN i.usuario u
    """
    )
    fun findAllCursosComUsuarios(): List<CursoComAlunoDTO>

    @Query (
        """
        SELECT
        new techForAll.techPoints.dto.RhAlunoCursoDto(
            c.idCurso,
            c.nome,
            u.idUsuario,
            u.nomeUsuario,
            e.cidade
        )
        FROM Curso c
        JOIN c.inscricoes i
        JOIN i.usuario u
        JOIN u.endereco e
        WHERE (:municipio IS NULL OR e.cidade = :municipio)
        AND (:nomeCurso IS NULL OR c.nome = :nomeCurso)
        """
    )
    fun findAllUsuariosComCurso(municipio: String?, nomeCurso: String?): List<RhAlunoCursoDto>


// TODO: Preciso Escolaridade dos Alunos

    // Implementação (escolaridade: String?,)
    // Query: AND (:escolaridade IS NULL OR u.escolaridade = :escolaridade)


    @Query (
        """
        SELECT
        new techForAll.techPoints.dto.RhAlunoCursoDto(
            c.idCurso,
            c.nome,
            u.idUsuario,
            u.nomeUsuario,
            u.imagemPerfil,
            u.email,
            u.inscricoes,
            e.cidade
        )
        FROM Curso c
        JOIN c.inscricoes i
        JOIN i.usuario u
        JOIN u.endereco e
        WHERE (c.idCurso = :idCurso)
        AND (u.idUsuario = :idUsuario)
        """
    )
    fun findByIdCursoAndByIdUsuario(idCurso: Int, idUsuario: Int): Optional<RhAlunoCursoDto>
}
