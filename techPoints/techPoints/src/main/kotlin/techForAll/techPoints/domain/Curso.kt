package techForAll.techPoints.domain

import jakarta.persistence.*

@Entity
class Curso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var nome: String,

    @Column(nullable = false)
    var totalAtividades: Int,

    @Column(nullable = false)
    var totalAtividadesDoAluno: Int,
)

