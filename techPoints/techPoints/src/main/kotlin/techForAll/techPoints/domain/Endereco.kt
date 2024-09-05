package techForAll.techPoints.domain

import jakarta.persistence.*

@Entity
class Endereco(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val cep: String,

    @Column(nullable = false)
    val logradouro: String,

    @Column(nullable = false)
    val numero: String,

    @Column(nullable = false)
    val cidade: String,

    @Column(nullable = false)
    val estado: String,

    @Column(nullable = false)
    val dataAtualizacao: LocalDateTime
)
