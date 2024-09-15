package techForAll.techPoints.dominio

import jakarta.persistence.*
import techForAll.techPoints.domain.Usuario
import java.time.LocalDateTime

@Entity
@Table(name = "pontuacao")
data class PontuacaoOld(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pontuacao")
    val idPontuacao: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    val usuario: Usuario,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ponto")
    val ponto: Ponto,

    @Column(name = "total_pontos_usuario")
    val totalPontosUsuario: Int,

    @Column(name = "data_atualizacao")
    val dataAtualizacao: LocalDateTime
)
