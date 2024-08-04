package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.Usuario
import java.util.Optional

interface UsuarioPersistence {

    fun findAll(): List<Usuario>

    fun findById(idUsuario: Int): Optional<Usuario>

    fun findByEmail(email: String): Optional<Usuario>

    fun existsByEmail(email: String): Boolean

    fun findByEmailAndSenha(email: String, senha: String): Optional<Usuario>

    fun deletar(idUsuario: Int)

    fun save(usuario: Usuario): Usuario
}
