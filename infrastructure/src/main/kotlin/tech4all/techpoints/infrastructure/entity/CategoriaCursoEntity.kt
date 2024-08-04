package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*

@Entity
@Table(name = "categoria_curso")
public data class CategoriaCursoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_curso")
    var idCategoriaCurso: Int,

    @Column(name = "nome", length = 45, nullable = false)
    var nome: String,
)
