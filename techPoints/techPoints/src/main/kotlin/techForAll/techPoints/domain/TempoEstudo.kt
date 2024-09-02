package techForAll.techPoints.domain

import jakarta.persistence.*


@Entity
class TempoEstudo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val nomeDia: String,

    @Column(nullable = false)
    val qtdTempoEstudo: String,

    @Column(nullable = false)
    val ativado: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metaEstudoSemana_id")
    val metaEstudoSemana: MetaEstudoSemana
)
