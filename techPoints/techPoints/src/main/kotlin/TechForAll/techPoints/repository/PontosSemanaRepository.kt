package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Ponto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PontosSemanaRepository : JpaRepository<Ponto, Int>{

    @Query("""
        SELECT 
            u.id_usuario AS idUsuario,
            u.nome_usuario AS nomeUsuario,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) THEN p.qtd_ponto ELSE 0 END), 0) AS pontosSemanaAtual,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) - 1 THEN p.qtd_ponto ELSE 0 END), 0) AS pontosSemanaPassada,
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) THEN p.qtd_ponto ELSE 0 END), 0) -
            COALESCE(SUM(CASE WHEN WEEK(p.data_entrega) = WEEK(CURRENT_DATE) - 1 THEN p.qtd_ponto ELSE 0 END), 0) AS diferencaPontos
        FROM
            Ponto p
        JOIN 
            Usuario u ON p.fk_usuario = u.id_usuario
        WHERE 
            u.id_usuario = :idUsuario
        GROUP BY 
            u.id_usuario, u.nome_usuario
    """, nativeQuery = true)
    fun findPontosSemana(@Param("idUsuario") idUsuario: Int): List<Array<Any>>

}