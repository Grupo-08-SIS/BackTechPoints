package TechForAll.techPoints.dominio

import jakarta.persistence.*

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


@Entity
@Table(name = "endereco")
data class Endereco (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    var id: Int,

    @NotNull
    @Size(min = 9, max = 9)
    @Column(name = "cep")
    var cep: String,

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "cidade")
    var cidade: String,

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "estado")
    var estado: String,

    @Column(name = "data_atualizacao")
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),
)