package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.entity.UsuarioEntity
import tech4all.techpoints.infrastructure.repository.UsuarioRepository

@Component
class UsuarioAdapter(
    private val usuarioRepository: UsuarioRepository
) {

    fun findByEmail(email: String): UsuarioEntity? {
        return usuarioRepository.findByEmail(email)
    }

    fun existsByEmail(email: String): Boolean {
        return usuarioRepository.existsByEmail(email)
    }

    fun findByEmailAndSenha(email: String, senha: String): UsuarioEntity? {
        return usuarioRepository.findByEmailAndSenha(email, senha)
    }

    fun deletar(idUsuario: Int) {
        usuarioRepository.deletar(idUsuario)
    }
}
