package TechForAll.techPoints.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "redefinicao_senha")
data class RedefinicaoSenha(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_redefinicao_senha")
    val idRedefinicaoSenha: Int?,

    @Column(name = "codigo_redefinicao")
    @Size(max = 8)
    val codigoRedefinicao: String,

    @Column(name = "data_criacao")
    val dataCriacao: LocalDateTime,

    @Column(name = "data_expiracao")
    val dataExpiracao: LocalDateTime,

    @Column(name = "valido")
    val valido: Boolean,

    @Column(name = "email_redefinicao")
    val emailRedefinicao: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    var fkUsuario: Usuario?
)