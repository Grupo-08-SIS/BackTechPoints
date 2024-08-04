package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*


@Entity
@Table(name = "curso")
public data class CursoEntity(

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
    var categoriaCursoEntity: CategoriaCursoEntity?,
)
