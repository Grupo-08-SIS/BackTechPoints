package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dto.AlunoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DashboardRhRepository : JpaRepository<Usuario, Int> {
//    @Query("""
//        SELECT
//            u.id, u.nomeUsuario, u.primeiroNome, u.sobrenome, u.email, c.nome, e.cidade
//        FROM Usuario u
//        JOIN Inscricao i ON u.id = i.usuario.id
//        JOIN Curso c ON i.curso.id = c.id
//        JOIN Endereco e ON u.endereco.id = e.id
//        WHERE (:nomeCurso IS NULL OR c.nome = :nomeCurso)
//        AND (:cidade IS NULL OR e.cidade = :cidade)
//    """)
//    fun findUsuariosByCursoAndCidade(
//        @Param("nomeCurso") nomeCurso: String?,
//        @Param("cidade") cidade: String?
//    ): List<AlunoDTO>
}