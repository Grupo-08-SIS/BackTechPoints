package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*

@Entity
@Table(name = "tipo_usuario")
public data class TipoUsuarioEntity (

    @Id
    @Column(name = "id_tipo_usuario")
    var idTipoUsuario: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "nome")
    var nome: NomeTipoUsuarioEntity

)