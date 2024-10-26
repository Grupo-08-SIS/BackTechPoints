package techForAll.techPoints.domain

import jakarta.persistence.*

@Entity
@Table(name = "categoria")
class Categoria(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var nome: String,

    @ManyToMany(mappedBy = "categorias")
    var cursos: List<CursoMoodle> = emptyList()
)