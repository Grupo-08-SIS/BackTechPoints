package techForAll.techPoints.domain

import jakarta.persistence.*

@Entity
@Table(name = "curso_moodle")
class CursoMoodle(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var nome: String,

    @ManyToMany
    @JoinTable(
        name = "curso_categoria",
        joinColumns = [JoinColumn(name = "curso_id")],
        inverseJoinColumns = [JoinColumn(name = "categoria_id")]
    )
    var categorias: List<Categoria> = emptyList()
)

