package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.Usuario
import tech4all.techpoints.domain.dto.UsuarioDTOInput
import tech4all.techpoints.domain.dto.UsuarioDTOOutput
import java.util.Optional

interface UsuarioService {

    fun findAll(): List<UsuarioDTOOutput>

    fun cadastrarUsuario(usuario: UsuarioDTOInput): UsuarioDTOOutput

    fun atualizarUsuario(idUsuario: Int, usuario: Map<String, Any>): UsuarioDTOOutput

    fun findByEmail(email: String): Optional<UsuarioDTOOutput>

    fun findById(idUsuario: Int): Optional<Usuario>

    fun buscarUsuarioPorId(idUsuario: Int): UsuarioDTOOutput

    fun existsByEmail(email: String): Boolean

    fun findByEmailAndSenha(email: String, senha: String): Optional<Usuario>

    fun getImagemPerfil(idUsuario: Int): ByteArray

    fun atualizarImagemPerfil(idUsuario: Int, novaFoto: ByteArray)

    fun login(email: String, senha: String): UsuarioDTOOutput

    fun logoff(idUsuario: Int)

    fun deletar(idUsuario: Int)

    fun softDeleteUsuario(idUsuario: Int)

    fun hardDeleteUsuario(idUsuario: Int)

}
