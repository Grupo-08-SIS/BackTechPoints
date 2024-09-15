package techForAll.techPoints.repository


import techForAll.techPoints.dominio.Ponto

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GraficoLinhaRepository : JpaRepository<Ponto, Int> {


    @Query(
        """
        SELECT
            u.idUsuario,
            i.curso.idCurso,
            i.curso.nome,
            p.dataEntrega,
            p.qtdPonto
        FROM Ponto p
        JOIN p.usuario u
        JOIN u.inscricoes i
        WHERE u.idUsuario = :idUsuario
    """
    )
    fun findPontosPorDiaECurso(@Param("idUsuario") idUsuario: Int): List<Array<Any>>


//    @Query(
//        """
//        SELECT
//            p.usuario,
//            p.atividade.modulo,
//            p.usuario.inscricoes.curso.nome,
//            p.dataEntrega,
//            p.qtdPonto
//        FROM PontosPorDiaECurso p
//        WHERE p.usuarioId = :idUsuario
//    """
//    )
}

