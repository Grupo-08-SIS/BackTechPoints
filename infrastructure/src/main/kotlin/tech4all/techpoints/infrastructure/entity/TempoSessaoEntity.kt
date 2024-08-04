package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*

@Entity
@Table(name = "tempo_sessao")
public data class TempoSessaoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tempo_sessao")
    var idTempoSessao: Int,

    @Column(name = "dia_sessao", nullable = false, length = 45)
    var diaSessao: String,

    @Column(name = "qtd_tempo_sessao", nullable = false, length = 8)
    var qtdTempo: String,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    var fkUsuarioEntity: Int
)