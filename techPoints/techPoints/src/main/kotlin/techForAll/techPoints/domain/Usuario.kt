package techForAll.techPoints.domain

import jakarta.persistence.*
    import java.time.LocalDateTime

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Usuario(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(nullable = false)
        var nomeUsuario: String,

        @Column(nullable = false, unique = true)
        var cpf: String,

        @Column(nullable = false)
        var senha: String,

        @Column(nullable = false)
        var primeiroNome: String,

        @Column(nullable = false)
        var sobrenome: String,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var telefone: String,

        @Column(nullable = true)
        var imagemPerfil: ByteArray?,

        @Column(nullable = false)
        var tipoUsuario: Int,

        @Column(nullable = false)
        var autenticado: Boolean,

        @Column(nullable = false)
        var dataCriacao: LocalDateTime = LocalDateTime.now(),

        @Column(nullable = true)
        var deletado: Boolean = false,

        @Column(nullable = true)
        var dataDeletado: LocalDateTime? = null,

        @Column(nullable = true)
        var dataAtualizacao: LocalDateTime? = null
) {
        abstract fun criarUsuario(endereco: Endereco?, empresa: DadosEmpresa?): Usuario
}
