package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Pontuacao(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var dataEntrega: String,

    @Column(nullable = false)
    var nomeAtividade: String,

    @Column(nullable = false)
    var notaAtividade: Double,

    var notaAluno: Double,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    var curso: Curso,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    var aluno: Aluno
) {
    // Funções adicionais, como cálculo de pontos, podem ser adicionadas aqui
}
