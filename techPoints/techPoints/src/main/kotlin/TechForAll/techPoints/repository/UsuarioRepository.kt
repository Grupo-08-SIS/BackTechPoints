package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository

interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByEmail(email: String) : Usuario
    fun existsByEmail(email: String): Boolean
    fun findByEmailAndSenha(email: String, senha: String) : Usuario
}
