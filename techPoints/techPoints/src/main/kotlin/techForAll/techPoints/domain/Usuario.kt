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

        @Column(nullable = false)
        var imagemPerfil: ByteArray,


        @Column(nullable = true)
        var autenticado: Boolean,

        @Column(nullable = false)
        var dataCriacao: LocalDateTime = LocalDateTime.now(),

        var deletado: Boolean = false,

        var dataDeletado: LocalDateTime? = null,

        var dataAtualizacao: LocalDateTime? = null
) {
        @PrePersist
        fun onPrePersist() {
                dataCriacao = LocalDateTime.now()
        }

        @PreUpdate
        fun onPreUpdate() {
                dataAtualizacao = LocalDateTime.now()
        }
        abstract fun criarUsuario(): Usuario
}
