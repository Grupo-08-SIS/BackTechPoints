package TechForAll.techPoints.dominio

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pontuacao")
class Pontuacao(
                @Id
                @GeneratedValue(strategy = GenerationType.IDENTITY)
                @Column(name = "id_pontuacao")
                var idPontuacao: Int,

                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
                var usuario: Usuario?,

                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "fk_pontos", referencedColumnName = "id_ponto")
                var ponto: Ponto? = null,


                @ManyToOne(fetch = FetchType.LAZY)
                @JoinColumn(name = "fk_tipo_ponto", referencedColumnName = "id_tipo_ponto")
                var tipoPonto : TipoPonto? = null,


                @Column(name = "total_pontos_usuario")
                var totalPontosUsuario: Int,

                @Column(name = "data_atualizacao")
                var dataAtualizacao: LocalDateTime = LocalDateTime.now(),
        )