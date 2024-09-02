package techForAll.techPoints.domain

import jakarta.persistence.*
    import java.time.LocalDateTime

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
abstract class Usuario(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @Column(nullable = false)
        val nomeUsuario: String,

        @Column(nullable = false, unique = true)
        val cpf: String,

        @Column(nullable = false)
        val senha: String,

        @Column(nullable = false)
        val primeiroNome: String,

        @Column(nullable = false)
        val sobrenome: String,

        @Column(nullable = false, unique = true)
        val email: String,

        @Column(nullable = false)
        val telefone: String,

        @Column(nullable = false)
        val imagemPerfil: String,

        @ManyToOne(fetch = FetchType.LAZY)
        @PrimaryKeyJoinColumn(name = "endereco_id")
        val endereco: Endereco,

        @Column(nullable = false)
        val enabled: Boolean,

        @Column(nullable = false)
        val dataCriacao: LocalDateTime = LocalDateTime.now(),

        val deletado: Boolean = false,

        val dataDeletado: LocalDateTime? = null,

        val dataAtualizacao: LocalDateTime? = null
) {
        abstract fun getTipoUsuario(): String
}