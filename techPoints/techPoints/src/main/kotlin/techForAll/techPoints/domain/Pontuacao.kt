package techForAll.techPoints.domain

import jakarta.persistence.*

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

    fun getPontosAtividade() : Int {

        val porcento = (this.notaAluno / this.notaAtividade) * 100;

        return when (porcento) {
            in 1.0..10.0  ->  10
            in 11.0..20.0  ->  20
            in 21.0..30.0  ->  30
            in 31.0..40.0  ->  40
            in 41.0..50.0  ->  50
            in 51.0..60.0  ->  60
            in 61.0..70.0  ->  70
            in 71.0..80.0  ->  80
            in 81.0..90.0  ->  90
            else -> {100}
        }
}
    // Funções adicionais, como cálculo de pontos, podem ser adicionadas aqui
}
