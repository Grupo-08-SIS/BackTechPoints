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



}
