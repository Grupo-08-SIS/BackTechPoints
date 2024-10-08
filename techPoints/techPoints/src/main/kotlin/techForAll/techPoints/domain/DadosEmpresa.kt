package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class DadosEmpresa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var nomeEmpresa: String,

    @Column(nullable = false, unique = true)
    var cnpj: String,

    @Column(nullable = false)
    var setorIndustria: String,

    @Column(nullable = false)
    var telefoneContato: String,

    @Column(nullable = false, unique = true)
    var emailCorporativo: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    var endereco: Endereco,

    @Column(nullable = false)
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var dataDeletado: LocalDateTime ? = null,

    @Column(nullable = true)
    var dataAtualizacao: LocalDateTime? = null,

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    var recrutadores: List<Recrutador> = listOf()
)