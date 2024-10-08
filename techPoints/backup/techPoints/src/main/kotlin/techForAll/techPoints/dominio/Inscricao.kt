package techForAll.techPoints.dominio

import jakarta.persistence.*
import techForAll.techPoints.domain.Usuario

@Entity
@Table(name = "inscricao")
data class Inscricao(

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_curso", referencedColumnName = "id_curso")
    val curso: CursoOld,

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    val usuario: Usuario? = null,

    @Column(name = "codigo_inscricao", length = 100)
    val codigoInscricao: String? = null,
)


