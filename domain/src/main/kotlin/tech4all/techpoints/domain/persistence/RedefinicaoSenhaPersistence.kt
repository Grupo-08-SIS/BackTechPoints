package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.RedefinicaoSenha
import java.time.LocalDateTime

interface RedefinicaoSenhaPersistence {

    fun findByEmailAndValidoAndDataExpiracaoAfter(email: String, dataAtual: LocalDateTime): List<RedefinicaoSenha>

    fun existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao: String, email: String): Boolean

    fun save(redefinicaoSenha: RedefinicaoSenha): RedefinicaoSenha
}
