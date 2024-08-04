package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.RedefinicaoSenhaDTO
import tech4all.techpoints.infrastructure.repository.RedefinicaoSenhaRepository
import java.time.LocalDateTime

@Component
class RedefinicaoSenhaAdapter(
    private val redefinicaoSenhaRepository: RedefinicaoSenhaRepository
) {

    fun findByEmailAndValidoAndDataExpiracaoAfter(email: String, dataAtual: LocalDateTime): List<RedefinicaoSenhaDTO> {
        val entities = redefinicaoSenhaRepository.findByEmailAndValidoAndDataExpiracaoAfter(email, dataAtual)
        return entities.map { entity ->
            entity.idRedefinicaoSenha?.let {
                RedefinicaoSenhaDTO(
                    id = it,
                    codigo = entity.codigoRedefinicao,
                    dataCriacao = entity.dataCriacao,
                    dataExpiracao = entity.dataExpiracao,
                    valido = entity.valido
                )
            }!!
        }
    }

    fun existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao: String, email: String): Boolean {
        return redefinicaoSenhaRepository.existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(codigoRedefinicao, email)
    }
}
