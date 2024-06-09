package TechForAll.techPoints.dominio

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(name = "atividade")
data class Atividade(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atividade")
    val id: Int,

    @Column(name = "nota")
    val nota: Int?,

    @Column(name = "temp_duracao")
    val duracao: java.sql.Time?,

    @Column(name = "data_entrega")
    val dataEntrega: Date?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns(
        JoinColumn(name = "fk_modulo"),
        JoinColumn(name = "fk_curso")
    )
    val modulo: Modulo
)
