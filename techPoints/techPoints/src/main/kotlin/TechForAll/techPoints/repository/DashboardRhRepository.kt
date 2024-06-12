package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dto.AlunoDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DashboardRhRepository : JpaRepository<Usuario, Int> {
    @Query(value = """
        SELECT 
            u.id_usuario AS idUsuario,
            u.nome_usuario AS nomeUsuario,
            u.primeiro_nome AS primeiroNome,
            u.sobrenome AS sobrenome,
            u.email AS email,
            c.nome AS nomeCurso,
            e.cidade AS cidade
        FROM 
            usuario u
        JOIN 
            tipo_usuario tu ON u.fk_tipo_usuario = tu.id_tipo_usuario
        LEFT JOIN 
            endereco e ON u.fk_endereco = e.id_endereco
        LEFT JOIN 
            inscricao i ON u.id_usuario = i.fk_usuario
        LEFT JOIN 
            curso c ON i.fk_curso = c.id_curso
        WHERE 
            tu.id_tipo_usuario = 2
        AND (:curso IS NULL OR c.nome = :curso)
        AND (:cidade IS NULL OR e.cidade = :cidade)
        LIMIT 0, 300;
    """, nativeQuery = true)
    fun findAlunosByCursoAndCidade(@Param("curso") curso: String?, @Param("cidade") cidade: String?): List<Any>
}