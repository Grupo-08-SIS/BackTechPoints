package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UsuarioRepository : JpaRepository<Usuario, Int> {
    fun findByEmail(email: String) : UserDetails
}
