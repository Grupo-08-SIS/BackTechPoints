package TechForAll.techPoints.repository


import TechForAll.techPoints.dominio.Ponto

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface GraficoLinhaRepository : JpaRepository<Ponto, Int> {


    @Query(value = """
        SELECT *
        FROM PontosPorDiaEporCursoPorUsuario
        WHERE usuario = :idUsuario
    """, nativeQuery = true)
    fun findPontosPorDiaECurso(@Param("idUsuario") idUsuario: Int): List<Array<Any>>


}

