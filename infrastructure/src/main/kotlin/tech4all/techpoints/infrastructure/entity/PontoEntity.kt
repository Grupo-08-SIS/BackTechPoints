package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "ponto")
public data class PontoEntity(

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
    val referenciaGerarPontuacaoEntity: ReferenciaGerarPontuacaoEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_atividade")
    val atividadeEntity: AtividadeEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario")
    val usuarioEntity: UsuarioEntity
)