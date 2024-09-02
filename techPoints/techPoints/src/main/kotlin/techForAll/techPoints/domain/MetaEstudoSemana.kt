package techForAll.techPoints.domain

import jakarta.persistence.*


@Entity
class MetaEstudoSemana(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    val aluno: Aluno,

    @OneToMany(mappedBy = "metaEstudoSemana")
    val diasAtivos: List<TempoEstudo>,

    @Column(nullable = false)
    val horasTotal: Double,

    @OneToMany(mappedBy = "metaEstudoSemana")
    val tempoSessao: List<TempoSessao>
)