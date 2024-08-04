package tech4all.techpoints.infrastructure.repository

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.entity.UsuarioEntity

@Repository
interface UsuarioRepository : JpaRepository<UsuarioEntity, Int> {

    fun findByEmail(email: String): UsuarioEntity?

    fun existsByEmail(email: String): Boolean

    fun findByEmailAndSenha(email: String, senha: String): UsuarioEntity?

    @Transactional
    @Modifying
    @Query("DELETE FROM UsuarioEntity u WHERE u.id = :idUsuario")
    fun deletar(@Param("idUsuario") idUsuario: Int)
}
