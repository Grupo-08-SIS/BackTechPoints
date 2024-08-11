package techForAll.techPoints.repository

import techForAll.techPoints.dominio.RedefinicaoSenha
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface RedefinicaoSenhaRepository : JpaRepository<RedefinicaoSenha, Int> {

    @Query("SELECT r.idRedefinicaoSenha, r.codigoRedefinicao, r.dataCriacao, r.dataExpiracao, r.valido FROM RedefinicaoSenha r WHERE r.emailRedefinicao = :email AND r.valido = true AND r.dataExpiracao > :dataAtual")
    fun findByEmailAndValidoAndDataExpiracaoAfter(@Param("email") emailUser: String, @Param("dataAtual") dataAtual: LocalDateTime): List<RedefinicaoSenha>

    @Query("SELECT COUNT(r) > 0 FROM RedefinicaoSenha r WHERE r.codigoRedefinicao = :codigoRedefinicao AND r.emailRedefinicao = :email AND r.valido = true AND r.dataExpiracao > CURRENT_TIMESTAMP")
    fun existsByTokenAndEmailAndValidoAndDataExpiracaoAfter(
        @Param("codigoRedefinicao") codigoRedefinicao: String,
        @Param("email") email: String
    ): Boolean




}

