package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "aluno")
class Aluno(

    @ManyToMany
    @JoinTable(
        name = "aluno_curso",
        joinColumns = [JoinColumn(name = "aluno_id")],
        inverseJoinColumns = [JoinColumn(name = "curso_id")]
    )
    var cursos: List<Curso>?,

    @Column(nullable = false)
    var escolaridade: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    var endereco: Endereco,

    @Column(nullable = true)
    var descricao: String? = null,

    @Column(nullable = false)
    var dtNasc: LocalDate,

    nomeUsuario: String,
    cpf: String,
    senha: String,
    primeiroNome: String,
    sobrenome: String,
    email: String,
    telefone: String,
    imagemPerfil: ByteArray,
    autenticado: Boolean
) : Usuario(
    nomeUsuario = nomeUsuario,
    cpf = cpf,
    senha = senha,
    primeiroNome = primeiroNome,
    sobrenome = sobrenome,
    email = email,
    telefone = telefone,
    imagemPerfil = imagemPerfil,
    autenticado = autenticado
) {
    override fun criarUsuario(): Usuario {
        return Aluno(
            nomeUsuario = this.nomeUsuario,
            cpf = this.cpf,
            senha = this.senha,
            primeiroNome = this.primeiroNome,
            sobrenome = this.sobrenome,
            email = this.email,
            telefone = this.telefone,
            imagemPerfil = this.imagemPerfil,
            endereco = this.endereco,
            autenticado = this.autenticado
        )
    }
}


