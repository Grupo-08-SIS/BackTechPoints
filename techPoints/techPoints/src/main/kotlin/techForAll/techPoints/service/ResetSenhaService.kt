package techForAll.techPoints.service

import techForAll.techPoints.dominio.RedefinicaoSenha
import techForAll.techPoints.repository.RedefinicaoSenhaRepository
import techForAll.techPoints.repository.UsuarioRepository
import org.springframework.http.ResponseEntity
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class ResetSenhaService(
    private val emailSender: JavaMailSender,
    private val redefinicaoSenhaRepository: RedefinicaoSenhaRepository,
    private val usuarioRepository: UsuarioRepository
) {

    fun senhaReset(emailUser: String): ResponseEntity<Any> {
        return try {
            val trocasSenhaAtivas = redefinicaoSenhaRepository.findByEmailAndValidoAndDataExpiracaoAfter(emailUser, LocalDateTime.now())
            val usuario = usuarioRepository.findByEmail(emailUser)

            if (trocasSenhaAtivas.isNotEmpty()) {
                ResponseEntity.status(409).body(mapOf("message" to "Uma troca de senha já está em andamento para este usuário."))
            } else {
                val codigoRecuperacao = gerarResetCode()
                val dataCriacao = LocalDateTime.now()
                val dataExpiracao = calcularValidade()
                val emailCadastrar: String = emailUser

                val redefinicaoSenha = RedefinicaoSenha(
                    idRedefinicaoSenha = 0,
                    codigoRedefinicao = codigoRecuperacao,
                    dataCriacao = dataCriacao,
                    dataExpiracao = dataExpiracao,
                    valido = true,
                    emailRedefinicao = emailCadastrar,
                    fkUsuario = usuario
                )
                redefinicaoSenhaRepository.save(redefinicaoSenha)// Usando o método toEntity()
                enviarEmailRecuperacaoSenha(emailCadastrar, codigoRecuperacao)
                ResponseEntity.status(200).build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
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

    fun verificarToken(codigoRedefinicao: String, emailUser: String): ResponseEntity<Any> {
        return try {
            if (redefinicaoSenhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao, emailUser)) {
                ResponseEntity.status(200).build()
            } else {
                ResponseEntity.status(400).body(mapOf("message" to "Token ou email inválido"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    fun atualizarSenha(emailUser: String, novaSenha: String): ResponseEntity<Any> {
        return try {
            val usuario = usuarioRepository.findByEmail(emailUser)

            if (usuario != null) {
                usuario.senha = novaSenha
                usuario.dataAtualizacao = LocalDateTime.now()
                usuarioRepository.save(usuario)
                ResponseEntity.status(200).build()
            } else {
                ResponseEntity.status(404).body(mapOf("message" to "Usuário não encontrado"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

}
