package techForAll.techPoints.domain

import jakarta.persistence.*
import techForAll.techPoints.dominio.Curso
import techForAll.techPoints.dtos.PontosPorCursoDto
import java.util.Date

@Entity
@DiscriminatorValue("1")
@PrimaryKeyJoinColumn(name = "id")
class Aluno(
    endereco: Endereco,

    @ManyToMany
    @JoinTable(
        name = "aluno_curso",
        joinColumns = [JoinColumn(name = "aluno_id")],
        inverseJoinColumns = [JoinColumn(name = "curso_id")]
    )
    val cursos: List<Curso>,

    @Column(nullable = true)
    val escolaridade: String? = null,

    @Column(nullable = false)
    val emblemas: Boolean = false,

    @Column(nullable = false)
    val descricao: String? = null,

    @Column(nullable = false)
    val dtNasc: Date
) : Usuario(
    nomeUsuario = "NomeUsuario",
    cpf = "CPF",
    senha = "Senha",
    primeiroNome = "PrimeiroNome",
    sobrenome = "Sobrenome",
    email = "Email",
    telefone = "Telefone",
    imagemPerfil = "ImagemPerfil",
    endereco = endereco,
    enabled = true
) {
    override fun getTipoUsuario(): String {
        return "ALUNO"
    }

    fun getIdade(): Int {
        // Implementação do cálculo de idade
        return 0
    }
}
