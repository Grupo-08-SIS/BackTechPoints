package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.RedefinicaoSenha
import java.time.LocalDateTime

interface RedefinicaoSenhaService {

    fun findByEmailAndValidoAndDataExpiracaoAfter(email: String, dataAtual: LocalDateTime): List<RedefinicaoSenha>

    fun existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao: String, email: String): Boolean

}
