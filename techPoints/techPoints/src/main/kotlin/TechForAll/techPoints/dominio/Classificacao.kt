package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "classificacao")
data class Classificacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_classificacao")
    val idClassificacao: Int,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    val usuario: Usuario,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_pontuacao")
    val pontuacao: Pontuacao,

    @Column(name = "total_pontos")
    val totalPontos: Int
)