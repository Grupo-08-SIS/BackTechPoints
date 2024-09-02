package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class TempoSessao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val diaSessao: LocalDateTime,

    @Column(nullable = false)
    val qtTempoSessao: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    val aluno: Aluno,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "metaEstudoSemana_id")
    val metaEstudoSemana: MetaEstudoSemana
)