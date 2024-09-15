package techForAll.techPoints.service


import techForAll.techPoints.domain.Usuario
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Recrutador
import techForAll.techPoints.dtos.UsuarioInput
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.DadosEmpresaRepository
import techForAll.techPoints.repository.EnderecoRepository
import techForAll.techPoints.repository.RecrutadorRepository


@Service
class UsuarioService @Autowired constructor(
    private val alunoRepository: AlunoRepository,
    private val recrutadorRepository: RecrutadorRepository,
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
}
//
//    fun softDeleteUsuario(email: String, senha: String) {
//        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
//                ?: throw IllegalArgumentException("Credenciais inválidas")
//
//        usuario.deletado = true
//        usuario.dataDeletado = LocalDateTime.now()
//        usuario.dataAtualizacao = LocalDateTime.now()
//        usuarioRepository.save(usuario)
//    }
//
//    fun hardDeleteUsuario(email: String, senha: String) {
//        val usuario: Usuario = usuarioRepository.findByEmailAndSenha(email, senha)
//        if (usuario != null) {
//            usuarioRepository.deletar(usuario.idUsuario)
//        } else {
//            throw NoSuchElementException("Usuário não encontrado")
//        }
//    }
//
//    fun listarUsuarios(): List<UsuarioDTOOutput> {
//        return usuarioRepository.findAll().map { usuarioParaDTOOutput(it) }
//    }
//
//    fun buscarUsuarioPorId(idUsuario: Int): UsuarioDTOOutput {
//        val usuario = usuarioRepository.findById(idUsuario)
//                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
//        return usuarioParaDTOOutput(usuario)
//    }
//
//    fun loginUsuario(email: String, senha: String): Any {
//        val usuario = usuarioRepository.findByEmail(email)
//
//        if (senha != usuario.senha) {
//            throw IllegalArgumentException("Credenciais inválidas")
//        }
//
//        usuario.autenticado = true
//        usuarioRepository.save(usuario)
//
//        return usuarioParaDTOOutput(usuario)
//    }
//
//    fun logoffUsuario(idUsuario: Int) {
//        val usuario = usuarioRepository.findById(idUsuario)
//                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
//
//        usuario.autenticado = false
//        usuarioRepository.save(usuario)
//
//    }
//
//    fun buscarUsuarioPorEmail(email: String): UsuarioDTOOutput {
//        val usuario = usuarioRepository.findByEmail(email)
//        if (usuario == null) {
//            throw NoSuchElementException("Usuário não encontrado") //nao consigo disparar isso, sempre da erro 500
//        }
//        return usuarioParaDTOOutput(usuario)
//    }
//
//    fun atualizarUsuario(idUsuario: Int, atualizacao: Map<String, Any>): UsuarioDTOOutput {
//        val usuarioExistente = usuarioRepository.findById(idUsuario)
//                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
//
//        atualizacao["nomeUsuario"]?.let { usuarioExistente.nomeUsuario = it as String }
//        atualizacao["cpf"]?.let { usuarioExistente.cpf = it as String }
//        atualizacao["primeiroNome"]?.let { usuarioExistente.primeiroNome = it as String }
//        atualizacao["sobrenome"]?.let { usuarioExistente.sobrenome = it as String }
//        atualizacao["email"]?.let { usuarioExistente.email = it as String }
//
//        usuarioExistente.dataAtualizacao = LocalDateTime.now()
//        val usuarioAtualizado = usuarioRepository.save(usuarioExistente)
//        return usuarioParaDTOOutput(usuarioAtualizado)
//    }
//
//
//    fun atualizarImagemUsuario(idUsuario: Int, novaFoto: ByteArray) {
//        if (novaFoto.isEmpty()) {
//            throw IllegalArgumentException("Requisição inválida")
//        }
//
//        val usuario = usuarioRepository.findById(idUsuario)
//                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
//
//        usuario.imagemPerfil = novaFoto
//        usuarioRepository.save(usuario)
//    }
//
//    fun obterImagemPerfil(idUsuario: Int): ByteArray {
//        val usuario = usuarioRepository.findById(idUsuario)
//                .orElseThrow { NoSuchElementException("Usuário não encontrado") }
//
//        return usuario.imagemPerfil ?: throw NoSuchElementException("Imagem de perfil não encontrada")
//    }
//
//
//    private fun usuarioParaDTOOutput(usuario: Usuario): UsuarioDTOOutput {
//        return UsuarioDTOOutput(
//                idUsuario = usuario.idUsuario,
//                nomeUsuario = usuario.nomeUsuario,
//                cpf = usuario.cpf,
//                senha = usuario.senha,
//                primeiroNome = usuario.primeiroNome,
//                sobrenome = usuario.sobrenome,
//                email = usuario.email,
//                autenticado = usuario.autenticado,
//                dataCriacao = usuario.dataCriacao,
//                deletado = usuario.deletado,
//                dataDeletado = usuario.dataDeletado,
//                dataAtualizacao = usuario.dataAtualizacao,
//                endereco = usuario.endereco!!,
//                tipoUsuario = usuario.tipoUsuario!!.idTipoUsuario
//        )
//    }
//
//    fun reativarUsuario(email: String, senha: String) {
//        val usuario = usuarioRepository.findByEmailAndSenha(email, senha)
//            ?: throw NoSuchElementException("Usuário não encontrado")
//
//        if (usuario.deletado == true) {
//            usuario.deletado = false
//            usuarioRepository.save(usuario)
//        } else {
//            throw IllegalArgumentException("Usuário já está ativo")
//        }
//    }
//
//
//}
