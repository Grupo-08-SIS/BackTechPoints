package TechForAll.techPoints.dominio

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "usuario")
class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    var idUsuario: Int? = null,

    @Column(name = "nome_usuario", nullable = false, length = 100)
    var nomeUsuario: String,

    @Column(name = "CPF", length = 11)
    @Size(min = 11, max = 11, message = "CPF deve conter exatamente 11 caracteres")
    var cpf: String? = null,

    @Column(name = "senha", nullable = false, length = 200)
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,}\$",
        message = "Senha deve ter no mínimo 8 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial"
    )
    var senha: String,

    @Column(name = "primeiro_nome", length = 100)
    var primeiroNome: String? = null,

    @Column(name = "sobrenome", length = 100)
    var sobrenome: String? = null,

    @Column(name = "email", nullable = false, length = 45)
    @Email
    var email: String,

    @Column(name = "imagem_perfil", length = 20 * 1024 * 1024)
    var imagemPerfil: ByteArray? = null,

    @Column(name = "data_criacao")
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(name = "deletado", nullable = false)
    var deletado: Boolean = false,

    @Column(name = "data_deletado")
    var dataDeletado: LocalDateTime? = null,

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_endereco", referencedColumnName = "id_endereco")
    var endereco: Endereco? = null,

    @Enumerated(EnumType.STRING)
    var role: UserRole = UserRole.USER
) : UserDetails {

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return if (this.role === UserRole.ADMIN) {
            listOf(
                SimpleGrantedAuthority("ROLE_ADMIN"),
                SimpleGrantedAuthority("ROLE_USER")
            )
        } else {
            listOf(SimpleGrantedAuthority("ROLE_USER"))
        }
    }

    override fun getPassword(): String {
        return senha
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
