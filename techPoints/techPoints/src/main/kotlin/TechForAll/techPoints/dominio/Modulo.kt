package TechForAll.techPoints.dominio


import jakarta.persistence.*

@Entity
@Table(name = "modulo")
data class Modulo(
    @Id
    @Column(name = "id_modulo")
    val id: Int,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_curso")
    val curso: Curso,

    @Column(name = "qtd_atividade_feita")
    val quantidadeAtividadeFeita: Int,

    @Column(name = "qtd_atividade_total")
    val quantidadeAtividadeTotal: Int,

    @Column(name = "nome_modulo")
    val nome: String?
)
