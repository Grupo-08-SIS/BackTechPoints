package techForAll.techPoints.domain

import jakarta.persistence.*


@Entity
class TempoEstudo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var nomeDia: String,

    @Column(nullable = false)
    var qtdTempoEstudo: String,

    @Column(nullable = false)
    var ativado: Boolean,

    var metaAtingida: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meta_estudo_semana_id")
    var metaEstudoSemana: MetaEstudoSemana
)