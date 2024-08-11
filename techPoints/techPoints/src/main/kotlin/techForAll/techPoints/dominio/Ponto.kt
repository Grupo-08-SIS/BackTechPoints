package techForAll.techPoints.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ponto")
data class Ponto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ponto")
    val id: Int,

    @Column(name = "nota")
    val nota: Int,

    @Column(name = "qtd_ponto")
    val qtdPonto: Int,

    @Column(name = "data_entrega")
    val dataEntrega: LocalDateTime,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_referencia_gerar_pontuacao")
    val referenciaGerarPontuacao: ReferenciaGerarPontuacao,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_atividade")
    val atividade: Atividade,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    val usuario: Usuario
)