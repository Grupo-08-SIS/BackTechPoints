package techForAll.techPoints.repository

import techForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByEmail(email: String) : Usuario
    fun existsByEmail(email: String): Boolean
    fun findByEmailAndSenha(email: String, senha: String) : Usuario


    @Modifying
    @Query("DELETE FROM Usuario u WHERE u.idUsuario = :idUsuario")
    fun deletar(@Param("idUsuario") idUsuario: Int)
}
