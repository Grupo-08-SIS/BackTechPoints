package TechForAll.techPoints.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Entity
@Table(name = "usuario")
data class Usuario(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_usuario")
        var idUsuario: Int,

        @Column(name = "nome_usuario", nullable = false, length = 100)
        var nomeUsuario: String,

        @Column(name = "CPF", length = 11)
        @Size(min = 11, max = 11, message = "CPF deve conter exatamente 11 caracteres")
        var cpf: String,

        @Column(name = "senha", nullable = false, length = 200)
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$",
                message = "Senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
        )
        var senha: String,

        @Column(name = "primeiro_nome", length = 100)
        var primeiroNome: String?,

        @Column(name = "sobrenome", length = 100)
        var sobrenome: String?,

        @Column(name = "email", length = 45)
        @Email
        var email: String,

        @Column(name = "telefone", length = 20)
        var telefone: String?,

        @Column(name = "autenticado")
        var autenticado: Boolean?,

        @Column(name = "imagem_perfil", length = 20 * 1024 * 1024)
        var imagemPerfil: ByteArray?,

        @Column(name = "data_criacao")
        var dataCriacao: LocalDateTime = LocalDateTime.now(),

        @Column(name = "deletado", nullable = false)
        var deletado: Boolean = false,

        @Column(name = "data_deletado")
        var dataDeletado: LocalDateTime?,

        @Column(name = "data_atualizacao")
        var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

        @OneToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
        @JoinColumn(name = "fk_endereco", referencedColumnName = "id_endereco")
        var endereco: Endereco?,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_tipo_usuario", referencedColumnName = "id_tipo_usuario")
        var tipoUsuario: TipoUsuario?,

        @OneToMany(mappedBy = "usuario")
        val inscricoes: List<Inscricao> = ArrayList()
)
