package techForAll.techPoints.domain

import jakarta.persistence.*

@Entity
class Curso(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false)
    val totalAtividades: Int,

    @Column(nullable = false)
    val totalAtividadesDoAluno: Int
)
