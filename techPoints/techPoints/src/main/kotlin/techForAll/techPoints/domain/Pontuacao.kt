package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Pontuacao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    val dataEntrega: LocalDateTime,

    @Column(nullable = false)
    val nomeAtividade: String,

    @Column(nullable = false)
    val notaAtividade: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    val curso: Curso,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    val aluno: Aluno
) {
    // Funções adicionais, como cálculo de pontos, podem ser adicionadas aqui
}
