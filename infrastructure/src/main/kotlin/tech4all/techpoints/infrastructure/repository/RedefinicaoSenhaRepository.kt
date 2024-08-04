package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.entity.RedefinicaoSenhaEntity
import java.time.LocalDateTime

@Repository
interface RedefinicaoSenhaRepository : JpaRepository<RedefinicaoSenhaEntity, Int> {

    @Query("SELECT r FROM RedefinicaoSenhaEntity r WHERE r.emailRedefinicao = :email AND r.valido = true AND r.dataExpiracao > :dataAtual")
    fun findByEmailAndValidoAndDataExpiracaoAfter(
        @Param("email") emailUser: String,
        @Param("dataAtual") dataAtual: LocalDateTime
    ): List<RedefinicaoSenhaEntity>

    @Query("SELECT COUNT(r) > 0 FROM RedefinicaoSenhaEntity r WHERE r.codigoRedefinicao = :codigoRedefinicao AND r.emailRedefinicao = :email AND r.valido = true AND r.dataExpiracao > CURRENT_TIMESTAMP")
    fun existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(
        @Param("codigoRedefinicao") codigoRedefinicao: String,
        @Param("email") email: String
    ): Boolean
}
