package TechForAll.techPoints.service

import TechForAll.techPoints.dominio.RedefinicaoSenha
import TechForAll.techPoints.dominio.Usuario
import TechForAll.techPoints.dominio.UsuarioDTO
import TechForAll.techPoints.repository.RedefinicaoSenhaRepository
import TechForAll.techPoints.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UsuarioService(
    private val emailSender: JavaMailSender,
    private val redefinicaoSenhaRepository: RedefinicaoSenhaRepository
) {

    fun senhaReset(emailUser: String): ResponseEntity<Any> {
        // Busca os dados necessários usando a consulta personalizada
        val trocasSenhaAtivas = redefinicaoSenhaRepository.findByEmailAndValidoAndDataExpiracaoAfter(emailUser, LocalDateTime.now())

        if (trocasSenhaAtivas.isNotEmpty()) {
            return ResponseEntity.status(409).body("Uma troca de senha já está em andamento para este usuário.")
        } else {
            val codigoRecuperacao = gerarResetCode()
            val dataCriacao = LocalDateTime.now()
            val dataExpiracao = calcularValidade()
            val emailCadastrar: String = emailUser
            val redefinicaoSenha = RedefinicaoSenha(
                idRedefinicaoSenha = null,
                codigoRedefinicao = codigoRecuperacao,
                dataCriacao = dataCriacao,
                dataExpiracao = dataExpiracao,
                valido = true,
                emailRedefinicao = emailCadastrar
            )
            redefinicaoSenhaRepository.save(redefinicaoSenha)
            enviarEmailRecuperacaoSenha(emailCadastrar, codigoRecuperacao)
            return ResponseEntity.status(200).build()
        }
    }


    fun enviarEmailRecuperacaoSenha(emailCadastrar: String, token: String) {
        val message = SimpleMailMessage()
        message.setTo(emailCadastrar)
        message.setSubject("Recuperação de Senha")
        message.setText("Olá!\n\nPara redefinir sua senha do Tech4Points, use o seguinte código: $token\n\nAtenciosamente,\nEquipe Tech4All")

        emailSender.send(message)
    }

    fun gerarResetCode(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    fun calcularValidade(): LocalDateTime {
        return LocalDateTime.now().plusHours(24)
    }
    fun usuarioParaDTO(usuario: Usuario): UsuarioDTO {
        return UsuarioDTO(
            idUsuario = usuario.idUsuario,
            nomeUsuario = usuario.nomeUsuario,
            cpf = usuario.cpf,
            primeiroNome = usuario.primeiroNome,
            sobrenome = usuario.sobrenome,
            email = usuario.email,
            dataCriacao = usuario.dataCriacao,
            deletado = usuario.deletado,
            dataDeletado = usuario.dataDeletado,
            dataAtualizacao = usuario.dataAtualizacao,
            endereco = usuario.endereco
        )
    }
}