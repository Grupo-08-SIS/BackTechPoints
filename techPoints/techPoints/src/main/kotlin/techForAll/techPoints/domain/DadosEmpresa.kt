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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    var endereco: Endereco,

    @Column(nullable = false)
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    var recrutadores: List<Recrutador> = listOf()
)
