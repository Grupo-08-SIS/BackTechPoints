package techForAll.techPoints.dominio

import jakarta.persistence.*
import techForAll.techPoints.domain.Usuario

@Entity
@Table(name = "tempo_sessao")
data class TempoSessao (
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
    var fkUsuario: Usuario?
)