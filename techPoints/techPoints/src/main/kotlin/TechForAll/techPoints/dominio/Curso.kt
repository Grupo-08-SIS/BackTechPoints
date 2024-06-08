package TechForAll.techPoints.dominio

import jakarta.persistence.*


@Entity
@Table(name = "curso")
data class Curso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    var idCurso: Int,

    @Column(name = "nome", length = 45, nullable = false)
    var nome: String,

    @Column(name = "qtd_horas", nullable = false)
    var qtdHoras: Int,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_categoria_curso", referencedColumnName = "id_categoria_curso")
    var categoriaCurso: CategoriaCurso?,
)
