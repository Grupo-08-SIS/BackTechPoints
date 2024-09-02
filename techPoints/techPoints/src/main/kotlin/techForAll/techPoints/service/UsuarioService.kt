package techForAll.techPoints.service

import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.dominio.TipoUsuario
import techForAll.techPoints.domain.Usuario
import techForAll.techPoints.dto.UsuarioDTOInput
import techForAll.techPoints.dto.UsuarioDTOOutput
import techForAll.techPoints.repository.EnderecoRepository
import techForAll.techPoints.repository.TipoUsuarioRepository
import techForAll.techPoints.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.NoSuchElementException

@Service
class UsuarioService @Autowired constructor(
        private val usuarioRepository: UsuarioRepository,
        private val enderecoRepository: EnderecoRepository,
        private val tipoUsuarioRepository: TipoUsuarioRepository,

        ) {

    fun cadastrarUsuario(usuarioDTO: UsuarioDTOInput): UsuarioDTOOutput {
        if (usuarioRepository.existsByEmail(usuarioDTO.email)) {
            throw IllegalArgumentException("Email já cadastrado")
        }
        val tipo: TipoUsuario = tipoUsuarioRepository.findById(usuarioDTO.idTipo)
                .orElseThrow { IllegalArgumentException("Tipo não encontrado com o ID: ${usuarioDTO.idTipo}") }

        val endereco: Endereco? = enderecoRepository.findById(usuarioDTO.idEndereco)
                .orElseThrow { IllegalArgumentException("Endereço não encontrado com o ID: ${usuarioDTO.idEndereco}") }

        val usuario = Usuario(
                idUsuario = usuarioDTO.idUsuario ?: 0, //autoincrementa no banco de forma correta
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
                tipoUsuario = tipo,
                telefone = usuarioDTO.telefone
        )

        val usuarioSalvo = usuarioRepository.save(usuario)
        return usuarioParaDTOOutput(usuarioSalvo)
    }

    fun softDeleteUsuario(email: String, senha: String) {
        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
                ?: throw IllegalArgumentException("Credenciais inválidas")

        usuario.deletado = true
        usuario.dataDeletado = LocalDateTime.now()
        usuario.dataAtualizacao = LocalDateTime.now()
        usuarioRepository.save(usuario)
    }

    fun hardDeleteUsuario(email: String, senha: String) {
        val usuario: Usuario = usuarioRepository.findByEmailAndSenha(email, senha)
        if (usuario != null) {
            usuarioRepository.deletar(usuario.idUsuario)
        } else {
            throw NoSuchElementException("Usuário não encontrado")
        }
    }

    fun listarUsuarios(): List<UsuarioDTOOutput> {
        return usuarioRepository.findAll().map { usuarioParaDTOOutput(it) }
    }

    fun buscarUsuarioPorId(idUsuario: Int): UsuarioDTOOutput {
        val usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
        return usuarioParaDTOOutput(usuario)
    }

    fun loginUsuario(email: String, senha: String): Any {
        val usuario = usuarioRepository.findByEmail(email)

        if (senha != usuario.senha) {
            throw IllegalArgumentException("Credenciais inválidas")
        }

        usuario.autenticado = true
        usuarioRepository.save(usuario)

        return usuarioParaDTOOutput(usuario)
    }

    fun logoffUsuario(idUsuario: Int) {
        val usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        usuario.autenticado = false
        usuarioRepository.save(usuario)

    }

    fun buscarUsuarioPorEmail(email: String): UsuarioDTOOutput {
        val usuario = usuarioRepository.findByEmail(email)
        if (usuario == null) {
            throw NoSuchElementException("Usuário não encontrado") //nao consigo disparar isso, sempre da erro 500
        }
        return usuarioParaDTOOutput(usuario)
    }

    fun atualizarUsuario(idUsuario: Int, atualizacao: Map<String, Any>): UsuarioDTOOutput {
        val usuarioExistente = usuarioRepository.findById(idUsuario)
                .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        atualizacao["nomeUsuario"]?.let { usuarioExistente.nomeUsuario = it as String }
        atualizacao["cpf"]?.let { usuarioExistente.cpf = it as String }
        atualizacao["primeiroNome"]?.let { usuarioExistente.primeiroNome = it as String }
        atualizacao["sobrenome"]?.let { usuarioExistente.sobrenome = it as String }
        atualizacao["email"]?.let { usuarioExistente.email = it as String }

        usuarioExistente.dataAtualizacao = LocalDateTime.now()
        val usuarioAtualizado = usuarioRepository.save(usuarioExistente)
        return usuarioParaDTOOutput(usuarioAtualizado)
    }


    fun atualizarImagemUsuario(idUsuario: Int, novaFoto: ByteArray) {
        if (novaFoto.isEmpty()) {
            throw IllegalArgumentException("Requisição inválida")
        }

        val usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        usuario.imagemPerfil = novaFoto
        usuarioRepository.save(usuario)
    }

    fun obterImagemPerfil(idUsuario: Int): ByteArray {
        val usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        return usuario.imagemPerfil ?: throw NoSuchElementException("Imagem de perfil não encontrada")
    }


    private fun usuarioParaDTOOutput(usuario: Usuario): UsuarioDTOOutput {
        return UsuarioDTOOutput(
                idUsuario = usuario.idUsuario,
                nomeUsuario = usuario.nomeUsuario,
                cpf = usuario.cpf,
                senha = usuario.senha,
                primeiroNome = usuario.primeiroNome,
                sobrenome = usuario.sobrenome,
                email = usuario.email,
                autenticado = usuario.autenticado,
                dataCriacao = usuario.dataCriacao,
                deletado = usuario.deletado,
                dataDeletado = usuario.dataDeletado,
                dataAtualizacao = usuario.dataAtualizacao,
                endereco = usuario.endereco!!,
                tipoUsuario = usuario.tipoUsuario!!.idTipoUsuario
        )
    }

    fun reativarUsuario(email: String, senha: String) {
        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
            ?: throw NoSuchElementException("Usuário não encontrado")

        if (usuario.deletado == true) {
            usuario.deletado = false
            usuarioRepository.save(usuario)
        } else {
            throw IllegalArgumentException("Usuário já está ativo")
        }
    }


}
