package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "tipo_ponto")
class TipoPonto(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_tipo_ponto")
        var idTipoPonto: Int,

        @Column(name = "nome", nullable = false, length = 100)
        var nome: String

)