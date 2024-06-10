package TechForAll.techPoints.dominio


import jakarta.persistence.*

@Entity
@Table(name = "modulo")
data class Modulo(
    @Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modulo")
    val idModulo: Int,

    @Id
    @Column(name = "fk_curso")
    val cursoId: Int,

    @Column(name = "qtd_atividade_total")
    val qtdAtividadeTotal: Int,

    @Column(name = "nome_modulo")
    val nomeModulo: String?
)