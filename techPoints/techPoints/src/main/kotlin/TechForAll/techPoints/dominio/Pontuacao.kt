package TechForAll.techPoints.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pontuacao")
data class Pontuacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val idPontuacao: Int? = null,

    @Column(name = "fk_usuario", nullable = false)
    val fkUsuario: Int?,

    @Column(name = "fk_pontos", nullable = false)
    val fkPonto: Int?,

    @Column(name = "fk_tipo_ponto", nullable = false)
    val fkTipoPonto: Int?,

    @Column(name = "total_pontos_usuario")
    val totalPontosUsuario: Int? = null,

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime? = null
)