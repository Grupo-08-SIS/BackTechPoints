package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class DadosEmpresa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nomeEmpresa: String,

    @Column(nullable = false, unique = true)
    val cnpj: String,

    @Column(nullable = false)
    val setor: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    val endereco: Endereco,

    @Column(nullable = false)
    val dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @OneToMany
    val recrutadores:  List<Recrutador>
)
