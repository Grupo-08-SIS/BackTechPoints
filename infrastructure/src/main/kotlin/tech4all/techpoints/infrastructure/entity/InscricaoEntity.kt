package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*

@Entity
@Table(name = "inscricao")
public data class InscricaoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscricao")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_curso", referencedColumnName = "id_curso")
    val cursoEntity: CursoEntity,

    @Column(name = "codigo_inscricao", length = 100)
    val codigoInscricao: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_usuario", referencedColumnName = "id_usuario")
    val usuarioEntity: UsuarioEntity? = null,
)


