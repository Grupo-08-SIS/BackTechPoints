package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "tipo_usuario")
data class TipoUsuario (
    @Id
    @Column(name = "id_tipo_usuario")
    var idTipoUsuario: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "nome")
    var nome: NomeTipoUsuario

)