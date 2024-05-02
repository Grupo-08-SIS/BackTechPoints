package TechForAll.techPoints.services

import TechForAll.techPoints.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthorizationService : UserDetailsService {
    @Autowired
    lateinit var repository: UsuarioRepository

    override fun loadUserByUsername(email: String): UserDetails {
        return repository.findByEmail(email)
    }
}