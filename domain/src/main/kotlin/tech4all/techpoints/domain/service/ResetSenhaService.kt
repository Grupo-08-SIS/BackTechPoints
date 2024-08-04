package tech4all.techpoints.domain.service

import org.springframework.http.ResponseEntity
import java.time.LocalDateTime

interface ResetSenhaService {

    fun senhaReset(emailUser: String): ResponseEntity<Any>

    fun enviarEmailRecuperacaoSenha(emailCadastrar: String, token: String)

    fun gerarResetCode(): String

    fun calcularValidade(): LocalDateTime

    fun verificarToken(codigoRedefinicao: String, emailUser: String): ResponseEntity<Any>

    fun atualizarSenha(emailUser: String, novaSenha: String): ResponseEntity<Any>
}
