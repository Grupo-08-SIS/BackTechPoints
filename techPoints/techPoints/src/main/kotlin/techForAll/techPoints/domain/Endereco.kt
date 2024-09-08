package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Endereco(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var cep: String,

    @Column(nullable = false)
    var logradouro: String,

    @Column(nullable = false)
    var numero: String,

    @Column(nullable = false)
    var cidade: String,

    @Column(nullable = false)
    var estado: String,

    @Column(nullable = true)
    var deletado: Boolean = false,

    @Column(nullable = false)
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = true)
    var dataDeletado: LocalDateTime? = null,

    @Column(nullable = true)
    var dataAtualizacao: LocalDateTime = LocalDateTime.now()

)


