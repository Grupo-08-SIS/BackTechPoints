package TechForAll.techPoints.dominio

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "dados_empresa")
data class DadosEmpresa (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empresa")
    var idEmpresa: Int,

    @Column(name = "nome_empresa", nullable = false, length = 100)
    var nomeEmpresa: String,

    @Column(name = "setor_industria", nullable = false, length = 100)
    var setorIndustria: String,

    @Column(name = "cargo_industria", nullable = false, length = 100)
    var cargoUsuario: String,

    @Column(name = "e-mail_corporativo", nullable = false, length = 100)
    var emailCorporativo: String,

    @Column(name = "telefone_contato_corporativo", nullable = false, length = 11)
    var telefoneContato: String,

    @Column(name = "cnpj", nullable = false, length = 14)
    var cnpj: String,

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    var fkUsuario: Usuario
)