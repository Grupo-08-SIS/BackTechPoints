package TechForAll.techPoints.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "redefinicao_senha")
data class RedefinicaoSenha(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_redefinicao_senha")
    val idRedefinicaoSenha: Int?,

    @Column(name = "codigo_redefinicao")
    val codigoRedefinicao: String,

    @Column(name = "data_criacao")
    val dataCriacao: LocalDateTime,

    @Column(name = "data_expiracao")
    val dataExpiracao: LocalDateTime,

    @Column(name = "valido")
    val valido: Boolean,

    @Column(name = "emailRedefinicao")
    val emailRedefinicao: String,
)