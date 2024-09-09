//package techForAll.techPoints.repository
//
//import techForAll.techPoints.dominio.Ponto
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//
//@Repository
//interface GraficoColunaRepository : JpaRepository<Ponto, Int> {
//    @Query(
//        """
//        SELECT
//            FUNCTION('MONTH', p.dataEntrega),
//            c.idCurso,
//            c.nome,
//            SUM(p.qtdPonto)
//        FROM Ponto p
//        JOIN p.usuario.inscricoes.curso c
//        WHERE p.usuario.idUsuario = :idUsuario
//        GROUP BY FUNCTION('MONTH', p.dataEntrega), c.idCurso, c.nome
//        ORDER BY FUNCTION('MONTH', p.dataEntrega), c.idCurso
//    """
//    )
//    fun findPontosPorCursoAoMes(@Param("idUsuario") idUsuario: Int): List<Array<Any>>
//}
//
