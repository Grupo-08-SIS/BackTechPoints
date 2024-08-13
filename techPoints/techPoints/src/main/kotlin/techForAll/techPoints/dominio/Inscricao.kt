package techForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "inscricao")
data class Inscricao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_curso", referencedColumnName = "id_curso")
    val curso: Curso,

    @Column(name = "codigo_inscricao", length = 100)
    val codigoInscricao: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    val usuario: Usuario? = null,
)


