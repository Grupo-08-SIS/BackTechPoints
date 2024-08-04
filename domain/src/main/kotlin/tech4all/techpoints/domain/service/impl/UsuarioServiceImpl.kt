package tech4all.techpoints.domain.service.impl

import org.springframework.stereotype.Service
import tech4all.techpoints.domain.model.Usuario
import tech4all.techpoints.domain.service.UsuarioService
import tech4all.techpoints.domain.persistence.EnderecoPersistence
import tech4all.techpoints.domain.persistence.TipoUsuarioPersistence
import tech4all.techpoints.domain.persistence.UsuarioPersistence
import tech4all.techpoints.domain.dto.UsuarioDTOInput
import tech4all.techpoints.domain.dto.UsuarioDTOOutput
import tech4all.techpoints.domain.mapper.UsuarioMapper
import java.time.LocalDateTime
import java.util.NoSuchElementException
import java.util.Optional

@Service
class UsuarioServiceImpl(
    private val usuarioPersistence: UsuarioPersistence,
    private val enderecoPersistence: EnderecoPersistence,
    private val tipoUsuarioPersistence: TipoUsuarioPersistence,
    private val usuarioMapper: UsuarioMapper
) : UsuarioService {

    override fun findAll(): List<UsuarioDTOOutput> {
        return usuarioPersistence.findAll().map(usuarioMapper::toDTO);
    }

    override fun findById(idUsuario: Int): Optional<Usuario> {
        TODO("Not yet implemented")
    }

    override fun findByEmail(email: String): Optional<UsuarioDTOOutput> {
        return usuarioPersistence.findByEmail(email).map(usuarioMapper::toDTO)
    }

    override fun existsByEmail(email: String): Boolean {
        return usuarioPersistence.existsByEmail(email)
    }

    override fun findByEmailAndSenha(email: String, senha: String): Optional<Usuario> {
        return usuarioPersistence.findByEmailAndSenha(email, senha)
    }

    override fun deletar(idUsuario: Int) {
        val usuario = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        usuarioPersistence.deletar(usuario.idUsuario)
    }

   override fun cadastrarUsuario(usuarioDTO: UsuarioDTOInput): UsuarioDTOOutput {
        if (existsByEmail(usuarioDTO.email)) {
            throw IllegalArgumentException("Email já cadastrado")
        }

        val tipo = tipoUsuarioPersistence.findById(usuarioDTO.idTipo)
            .orElseThrow { IllegalArgumentException("Tipo não encontrado com o ID: ${usuarioDTO.idTipo}") }

        val endereco = enderecoPersistence.findById(usuarioDTO.idEndereco)
            .orElseThrow { IllegalArgumentException("Endereço não encontrado com o ID: ${usuarioDTO.idEndereco}") }

        val usuario = Usuario(
            idUsuario = usuarioDTO.idUsuario ?: 0,
            nomeUsuario = usuarioDTO.nomeUsuario,
            cpf = usuarioDTO.cpf!!,
            senha = usuarioDTO.senha,
            primeiroNome = usuarioDTO.primeiroNome,
            sobrenome = usuarioDTO.sobrenome,
            email = usuarioDTO.email,
            autenticado = usuarioDTO.autenticado,
            dataDeletado = usuarioDTO.dataDeletado,
            imagemPerfil = null,
            endereco = endereco,
            tipoUsuario = tipo
        )

        val usuarioSalvo = usuarioPersistence.save(usuario)
        return usuarioMapper.toDTO(usuarioSalvo)
    }

    override fun softDeleteUsuario(idUsuario: Int) {
        val usuario = findById(idUsuario)
            .orElseThrow {IllegalArgumentException("Usuário não encontrado")}

        usuario.deletado = true
        usuario.dataDeletado = LocalDateTime.now()
        usuario.dataAtualizacao = LocalDateTime.now()
        usuarioPersistence.save(usuario)
    }

  override  fun hardDeleteUsuario(idUsuario: Int) {
        val usuario = findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado")}

        deletar(usuario.idUsuario)
    }

    override fun buscarUsuarioPorId(idUsuario: Int): UsuarioDTOOutput {
        val usuario = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }
        return usuarioMapper.toDTO(usuario)
    }

    override fun login(email: String, senha: String): UsuarioDTOOutput {
        val usuario = findByEmail(email)
            .orElseThrow {NoSuchElementException("Usuário não encontrado com o email: $email") }

        if (senha != usuario.senha) {
            throw IllegalArgumentException("Credenciais inválidas")
        }

        usuario.autenticado = true
        val usuarioInput = usuarioMapper.toDtoInput(usuario)
        val usuarioDomain = usuarioMapper.toDomain(usuarioInput)
        usuarioPersistence.save(usuarioDomain)

        return usuarioMapper.toDTO(usuarioDomain)
    }

    override fun logoff(idUsuario: Int) {
        val usuario = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        usuario.autenticado = false
        usuarioPersistence.save(usuario)
    }

    override fun atualizarUsuario(idUsuario: Int, atualizacao: Map<String, Any>): UsuarioDTOOutput {
        val usuarioExistente = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        atualizacao["nomeUsuario"]?.let { usuarioExistente.nomeUsuario = it as String }
        atualizacao["cpf"]?.let { usuarioExistente.cpf = it as String }
        atualizacao["primeiroNome"]?.let { usuarioExistente.primeiroNome = it as String }
        atualizacao["sobrenome"]?.let { usuarioExistente.sobrenome = it as String }
        atualizacao["email"]?.let { usuarioExistente.email = it as String }

        usuarioExistente.dataAtualizacao = LocalDateTime.now()
        val usuarioAtualizado = usuarioPersistence.save(usuarioExistente)
        return usuarioMapper.toDTO(usuarioAtualizado)
    }

    override fun atualizarImagemPerfil(idUsuario: Int, novaFoto: ByteArray) {
        if (novaFoto.isEmpty()) {
            throw IllegalArgumentException("Requisição inválida")
        }

        val usuario = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        usuario.imagemPerfil = novaFoto
        usuarioPersistence.save(usuario)
    }

    override fun getImagemPerfil(idUsuario: Int): ByteArray {
        val usuario = usuarioPersistence.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        return usuario.imagemPerfil ?: throw NoSuchElementException("Imagem de perfil não encontrada")
    }

}
