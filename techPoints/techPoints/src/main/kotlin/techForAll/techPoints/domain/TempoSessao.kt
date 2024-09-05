package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class TempoSessao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val diaSessao: String,

    @Column(nullable = false)
    val qtdTempoSessao: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    val aluno: Aluno
)