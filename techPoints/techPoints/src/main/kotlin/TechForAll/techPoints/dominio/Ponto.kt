package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "ponto")
data class Ponto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "qtd_ponto")
    val qtdPonto: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_referencia_gerar_pontuacao")
    val referenciaGerarPontuacao: ReferenciaGerarPontuacao
)
