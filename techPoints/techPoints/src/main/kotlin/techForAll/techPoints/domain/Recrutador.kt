package techForAll.techPoints.domain

import jakarta.persistence.*


@Entity
@DiscriminatorValue("2")
@PrimaryKeyJoinColumn(name = "usuario_id")
class Recrutador(
    endereco: Endereco,
    @ManyToMany
    @JoinTable(
        name = "recrutador_favoritos",
        joinColumns = [JoinColumn(name = "recrutador_id")],
        inverseJoinColumns = [JoinColumn(name = "aluno_id")]
    )
    val favoritos: List<Aluno>,

    @ManyToMany
    @JoinTable(
        name = "recrutador_interesses",
        joinColumns = [JoinColumn(name = "recrutador_id")],
        inverseJoinColumns = [JoinColumn(name = "aluno_id")]
    )
    val interessados: List<Aluno>
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
        return "RECRUTADOR"
    }
}





