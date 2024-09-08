package techForAll.techPoints.domain

import jakarta.persistence.*
import techForAll.techPoints.dtos.ListToJsonConverter


@Entity
@Table(name = "recrutador")
class Recrutador(

    @Convert(converter = ListToJsonConverter::class)
    @Column(name = "favoritos_json", columnDefinition = "json")
    var favoritos: List<Long> = emptyList(),

    @Convert(converter = ListToJsonConverter::class)
    @Column(name = "interessados_json", columnDefinition = "json")
    var interessados: List<Long> = emptyList(),


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    var empresa: DadosEmpresa,

    @Column(nullable = false)
    var cargoUsuario: String,

    nomeUsuario: String,
    cpf: String,
    senha: String,
    primeiroNome: String,
    sobrenome: String,
    email: String,
    telefone: String,
    imagemPerfil: ByteArray?,
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
    tipoUsuario = 2,
    autenticado = autenticado
) {
    override fun criarUsuario(endereco: Endereco?, empresa: DadosEmpresa?): Usuario {
        if (empresa == null) throw IllegalArgumentException("Empresa é obrigatória para Recrutador")
        return Recrutador(
            favoritos = this.favoritos,
            interessados = this.interessados,
            empresa = empresa,
            cargoUsuario = this.cargoUsuario,
            nomeUsuario = this.nomeUsuario,
            cpf = this.cpf,
            senha = this.senha,
            primeiroNome = this.primeiroNome,
            sobrenome = this.sobrenome,
            email = this.email,
            telefone = this.telefone,
            imagemPerfil = this.imagemPerfil,
            autenticado = this.autenticado
        )
    }
}








