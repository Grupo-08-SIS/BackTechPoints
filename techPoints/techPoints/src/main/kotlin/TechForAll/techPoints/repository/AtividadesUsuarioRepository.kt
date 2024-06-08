package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Pontuacao
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AtividadesUsuarioRepository : JpaRepository<Pontuacao, Int> {

    @Query("SELECT * FROM atividades_usuario WHERE id_usuario = :idUsuario", nativeQuery = true)
    fun findAtividadesUsuario(idUsuario: Int): List<Array<Any>>
}