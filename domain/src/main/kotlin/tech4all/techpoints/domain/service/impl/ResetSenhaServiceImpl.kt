package tech4all.techpoints.domain.service.impl

import org.springframework.http.ResponseEntity
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import tech4all.techpoints.domain.model.RedefinicaoSenha
import tech4all.techpoints.domain.persistence.RedefinicaoSenhaPersistence
import tech4all.techpoints.domain.persistence.UsuarioPersistence
import tech4all.techpoints.domain.service.ResetSenhaService
import java.time.LocalDateTime
import java.util.*

@Service
class ResetSenhaServiceImpl(
    private val emailSender: JavaMailSender,
    private val redefinicaoSenhaPersistence: RedefinicaoSenhaPersistence,
    private val usuarioPersistence: UsuarioPersistence
) : ResetSenhaService {

    override fun senhaReset(emailUser: String): ResponseEntity<Any> {
        return try {
            val trocasSenhaAtivas = redefinicaoSenhaPersistence.findByEmailAndValidoAndDataExpiracaoAfter(emailUser, LocalDateTime.now())
            val usuario = usuarioPersistence.findByEmail(emailUser)

            if (trocasSenhaAtivas.isNotEmpty()) {
                ResponseEntity.status(409).body(mapOf("message" to "Uma troca de senha já está em andamento para este usuário."))
            } else {
                val codigoRecuperacao = gerarResetCode()
                val dataCriacao = LocalDateTime.now()
                val dataExpiracao = calcularValidade()
                val redefinicaoSenha = RedefinicaoSenha(
                    idRedefinicaoSenha = 0,
                    codigoRedefinicao = codigoRecuperacao,
                    dataCriacao = dataCriacao,
                    dataExpiracao = dataExpiracao,
                    valido = true,
                    emailRedefinicao = emailUser,
                    fkUsuario = usuario
                )
                redefinicaoSenhaPersistence.save(redefinicaoSenha)
                enviarEmailRecuperacaoSenha(emailUser, codigoRecuperacao)
                ResponseEntity.status(200).build()
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    override fun enviarEmailRecuperacaoSenha(emailCadastrar: String, token: String) {
        val message = SimpleMailMessage()
        message.setTo(emailCadastrar)
        message.setSubject("Recuperação de Senha")
        message.setText("Olá!\n\nPara redefinir sua senha do Tech4Points, use o seguinte código: $token\n\nAtenciosamente,\nEquipe Tech4All")

        emailSender.send(message)
    }

    override fun gerarResetCode(): String {
        return UUID.randomUUID().toString().substring(0, 8)
    }

    override fun calcularValidade(): LocalDateTime {
        return LocalDateTime.now().plusHours(24)
    }

    override fun verificarToken(codigoRedefinicao: String, emailUser: String): ResponseEntity<Any> {
        return try {
            if (redefinicaoSenhaPersistence.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao, emailUser)) {
                ResponseEntity.status(200).build()
            } else {
                ResponseEntity.status(400).body(mapOf("message" to "Token ou email inválido"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }

    override fun atualizarSenha(emailUser: String, novaSenha: String): ResponseEntity<Any> {
        return try {
            val usuario = usuarioPersistence.findByEmail(emailUser)

            if (usuario != null) {
                usuario.get().senha = novaSenha
                usuario.get().dataAtualizacao = LocalDateTime.now()
                usuarioPersistence.save(usuario.get())
                ResponseEntity.status(200).build()
            } else {
                ResponseEntity.status(404).body(mapOf("message" to "Usuário não encontrado"))
            }
        } catch (e: Exception) {
            ResponseEntity.status(500).body(mapOf("message" to "Erro interno do servidor"))
        }
    }
}
