package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pontuacao")
public data class PontuacaoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pontuacao")
    val idPontuacao: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    val usuarioEntity: UsuarioEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ponto")
    val pontoEntity: PontoEntity,

    @Column(name = "total_pontos_usuario")
    val totalPontosUsuario: Int,

    @Column(name = "data_atualizacao")
    val dataAtualizacao: LocalDateTime
)
