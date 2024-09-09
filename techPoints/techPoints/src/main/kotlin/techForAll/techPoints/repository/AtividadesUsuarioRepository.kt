//package techForAll.techPoints.repository
//
//
//import techForAll.techPoints.dominio.Atividade
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//
//@Repository
//interface AtividadesUsuarioRepository : JpaRepository<Atividade, Int> {
//
//    @Query(
//        """
//        SELECT m.cursoId, SUM(m.qtdAtividadeTotal)
//        FROM Modulo m
//        GROUP BY m.cursoId
//    """
//    )
//    fun findTotalAtividadesPorCurso(): List<Array<Any>>
//
//    @Query(
//        """
//        SELECT
//            c.idCurso,
//            c.nome,
//            COUNT(p.atividade)
//        FROM Curso c
//        LEFT JOIN Ponto p ON c.idCurso = p.atividade.modulo.cursoId AND p.usuario.idUsuario = :idUsuario
//        GROUP BY c.idCurso, c.nome
//    """
//    )
//    fun findAtividadesPorCursoEUsuario(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
//}