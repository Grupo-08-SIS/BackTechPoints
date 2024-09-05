package techForAll.techPoints.domain

import jakarta.persistence.*


@Entity
class TempoEstudo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val nomeDia: String,

    @Column(nullable = false)
    val qtdTempoEstudo: String,

    @Column(nullable = false)
    val ativado: Boolean,

    var metaAtingida: Boolean = false
)
