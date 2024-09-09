package techForAll.techPoints.service


import techForAll.techPoints.domain.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Recrutador
import techForAll.techPoints.dtos.UsuarioInput
import techForAll.techPoints.repository.*
import java.time.LocalDate
import java.time.LocalDateTime


@Service
class UsuarioService @Autowired constructor(
    private val alunoRepository: AlunoRepository,
    private val recrutadorRepository: RecrutadorRepository,
    private val usuarioRepository: UsuarioRepository,
    private val dadosEmpresaRepository: DadosEmpresaRepository,
    private val enderecoRepository: EnderecoRepository

    ) {

    fun cadastrarUsuario(request: UsuarioInput): Usuario {
        return when (request.tipoUsuario) {
            1 -> {
                val endereco = enderecoRepository.findById(request.enderecoId!!)
                    .orElseThrow { IllegalArgumentException("Endereço não encontrado") }
                val aluno = Aluno(
                    nomeUsuario = request.nomeUsuario,
                    cpf = request.cpf,
                    senha = request.senha,
                    primeiroNome = request.primeiroNome,
                    sobrenome = request.sobrenome,
                    email = request.email,
                    telefone = request.telefone,
                    imagemPerfil = null,
                    dtNasc = request.dtNasc!!,
                    escolaridade = request.escolaridade!!,
                    autenticado = request.autenticado,
                    endereco = endereco
                )
                alunoRepository.save(aluno.criarUsuario(endereco, null) as Aluno)
            }

            2 -> {
                val empresa = dadosEmpresaRepository.findByCnpj(request.cnpj!!)
                    .orElseThrow { IllegalArgumentException("Empresa não encontrada com o CNPJ informado") }
                val recrutador = Recrutador(
                    favoritos = emptyList(),
                    interessados = emptyList(),
                    empresa = empresa,
                    cargoUsuario = request.cargoUsuario!!,
                    nomeUsuario = request.nomeUsuario,
                    cpf = request.cpf,
                    senha = request.senha,
                    primeiroNome = request.primeiroNome,
                    sobrenome = request.sobrenome,
                    email = request.email,
                    telefone = request.telefone,
                    imagemPerfil = null,
                    autenticado = request.autenticado
                )
                recrutadorRepository.save(recrutador.criarUsuario(null, empresa) as Recrutador)
            }

            else -> throw IllegalArgumentException("Tipo de usuário inválido")
        }
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
        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
            ?: throw NoSuchElementException("Usuário não encontrado")

        usuarioRepository.delete(usuario)
    }

    fun listarUsuarios(): List<Map<String, Any>> {
        return usuarioRepository.findAll().map { usuario ->
            when (usuario) {
                is Aluno -> mapOf(
                    "id" to usuario.id,
                    "nomeUsuario" to usuario.nomeUsuario,
                    "cpf" to usuario.cpf,
                    "primeiroNome" to usuario.primeiroNome,
                    "sobrenome" to usuario.sobrenome,
                    "email" to usuario.email,
                    "telefone" to usuario.telefone,
                    "tipoUsuario" to "Aluno",
                    "autenticado" to usuario.autenticado,
                    "dataCriacao" to usuario.dataCriacao,
                    "escolaridade" to usuario.escolaridade,
                    "dataNascimento" to usuario.dtNasc,
                    "endereco" to usuario.endereco
                )
                is Recrutador -> mapOf(
                    "id" to usuario.id,
                    "nomeUsuario" to usuario.nomeUsuario,
                    "cpf" to usuario.cpf,
                    "primeiroNome" to usuario.primeiroNome,
                    "sobrenome" to usuario.sobrenome,
                    "email" to usuario.email,
                    "telefone" to usuario.telefone,
                    "tipoUsuario" to "Recrutador",
                    "autenticado" to usuario.autenticado,
                    "dataCriacao" to usuario.dataCriacao,
                    "empresa" to usuario.empresa,
                    "cargoUsuario" to usuario.cargoUsuario
                )
                else -> throw IllegalStateException("Tipo de usuário desconhecido")
            }
        }
    }

    fun buscarUsuarioPorId(idUsuario: Long): Map<String, Any> {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        return when (usuario) {
            is Aluno -> mapOf(
                "id" to usuario.id,
                "nomeUsuario" to usuario.nomeUsuario,
                "cpf" to usuario.cpf,
                "primeiroNome" to usuario.primeiroNome,
                "sobrenome" to usuario.sobrenome,
                "email" to usuario.email,
                "telefone" to usuario.telefone,
                "tipoUsuario" to "Aluno",
                "autenticado" to usuario.autenticado,
                "dataCriacao" to usuario.dataCriacao,
                "escolaridade" to usuario.escolaridade,
                "dataNascimento" to usuario.dtNasc,
                "endereco" to usuario.endereco
            )
            is Recrutador -> mapOf(
                "id" to usuario.id,
                "nomeUsuario" to usuario.nomeUsuario,
                "cpf" to usuario.cpf,
                "primeiroNome" to usuario.primeiroNome,
                "sobrenome" to usuario.sobrenome,
                "email" to usuario.email,
                "telefone" to usuario.telefone,
                "tipoUsuario" to "Recrutador",
                "autenticado" to usuario.autenticado,
                "dataCriacao" to usuario.dataCriacao,
                "empresa" to usuario.empresa,
                "cargoUsuario" to usuario.cargoUsuario
            )
            else -> throw IllegalStateException("Tipo de usuário desconhecido")
        }
    }

    fun loginUsuario(email: String, senha: String): Usuario {
        val usuario = usuarioRepository.findByEmail(email)
            ?: throw IllegalArgumentException("Usuário não encontrado")

        usuario.login(senha)
        usuarioRepository.save(usuario)

        return usuario
    }

    fun logoffUsuario(idUsuario: Long) {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        usuario.logoff()
        usuarioRepository.save(usuario)
    }

    fun buscarUsuarioPorEmail(email: String): Map<String, Any> {
        val usuario = usuarioRepository.findByEmail(email)
            ?: throw NoSuchElementException("Usuário não encontrado")

        return when (usuario) {
            is Aluno -> mapOf(
                "id" to usuario.id,
                "nomeUsuario" to usuario.nomeUsuario,
                "cpf" to usuario.cpf,
                "primeiroNome" to usuario.primeiroNome,
                "sobrenome" to usuario.sobrenome,
                "email" to usuario.email,
                "telefone" to usuario.telefone,
                "tipoUsuario" to "Aluno",
                "autenticado" to usuario.autenticado,
                "dataCriacao" to usuario.dataCriacao,
                "escolaridade" to usuario.escolaridade,
                "dataNascimento" to usuario.dtNasc,
                "endereco" to usuario.endereco
            )
            is Recrutador -> mapOf(
                "id" to usuario.id,
                "nomeUsuario" to usuario.nomeUsuario,
                "cpf" to usuario.cpf,
                "primeiroNome" to usuario.primeiroNome,
                "sobrenome" to usuario.sobrenome,
                "email" to usuario.email,
                "telefone" to usuario.telefone,
                "tipoUsuario" to "Recrutador",
                "autenticado" to usuario.autenticado,
                "dataCriacao" to usuario.dataCriacao,
                "empresa" to usuario.empresa.nomeEmpresa,
                "cargoUsuario" to usuario.cargoUsuario
            )
            else -> throw IllegalStateException("Tipo de usuário desconhecido")
        }
    }

    fun atualizarUsuario(idUsuario: Long, atualizacao: Map<String, Any>): Usuario {
        val usuarioExistente = usuarioRepository.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado com o ID: $idUsuario") }

        atualizacao["nomeUsuario"]?.let { usuarioExistente.nomeUsuario = it as String }
        atualizacao["cpf"]?.let { usuarioExistente.cpf = it as String }
        atualizacao["primeiroNome"]?.let { usuarioExistente.primeiroNome = it as String }
        atualizacao["sobrenome"]?.let { usuarioExistente.sobrenome = it as String }
        atualizacao["email"]?.let { usuarioExistente.email = it as String }
        atualizacao["telefone"]?.let { usuarioExistente.telefone = it as String }
        usuarioExistente.dataAtualizacao = LocalDateTime.now()

        if (usuarioExistente is Aluno) {
            atualizacao["escolaridade"]?.let { usuarioExistente.escolaridade = it as String }
            atualizacao["dataNascimento"]?.let { usuarioExistente.dtNasc = it as LocalDate }
            atualizacao["descricao"]?.let { usuarioExistente.descricao = it as String }
        }

        if (usuarioExistente is Recrutador) {
            atualizacao["cargoUsuario"]?.let { usuarioExistente.cargoUsuario = it as String }
        }

        return usuarioRepository.save(usuarioExistente)
    }

    fun atualizarImagemUsuario(idUsuario: Long, novaFoto: ByteArray) {
        if (novaFoto.isEmpty()) {
            throw IllegalArgumentException("Requisição inválida")
        }

        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        usuario.imagemPerfil = novaFoto
        usuarioRepository.save(usuario)
    }

    fun obterImagemPerfil(idUsuario: Long): ByteArray {
        val usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow { NoSuchElementException("Usuário não encontrado") }

        return usuario.imagemPerfil ?: throw NoSuchElementException("Imagem de perfil não encontrada")
    }

    fun reativarUsuario(email: String, senha: String) {
        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
            ?: throw NoSuchElementException("Usuário não encontrado")

        try {
            usuario.reativar()
            usuarioRepository.save(usuario)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    }


}
