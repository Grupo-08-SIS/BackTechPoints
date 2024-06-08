package TechForAll.techPoints.repository

import TechForAll.techPoints.dto.AtividadesUsuarioDTO
import TechForAll.techPoints.dto.PontosSemanaDTO
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PontosSemanaRepository : JpaRepository<PontosSemanaDTO, Int>{

    @Query("SELECT * FROM pontuacao_usuario_semana WHERE id_usuario = :idUsuario", nativeQuery = true)
    fun findPontosSemana(idUsuario: Int): PontosSemanaDTO

}