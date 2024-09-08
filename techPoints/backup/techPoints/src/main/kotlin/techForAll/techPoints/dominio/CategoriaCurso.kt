package techForAll.techPoints.dominio

import jakarta.persistence.*


@Entity
@Table(name = "categoria_curso")
data class CategoriaCurso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_curso")
    var idCategoriaCurso: Int,

    @Column(name = "nome", length = 45, nullable = false)
    var nome: String,
)
