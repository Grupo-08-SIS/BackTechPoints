package techForAll.techPoints.dominio

import jakarta.persistence.*

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


@Entity
@Table(name = "endereco")
data class Endereco (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    @field:Column(name = "id_endereco")
    var id: Int,

    @field:NotNull
    @field:Size(min = 9, max = 9)
    @field:Column(name = "cep")
    var cep: String,

    @field:NotNull
    @field:Size(min = 2, max = 100)
    @field:Column(name = "cidade")
    var cidade: String,

    @field:NotNull
    @field:Size(min = 2, max = 2)
    @field:Column(name = "estado")
    var estado: String,

    @field:NotNull
    @field:Size(min = 2, max = 100)
    @field:Column(name = "rua")
    var rua: String,

    @field:Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),
)