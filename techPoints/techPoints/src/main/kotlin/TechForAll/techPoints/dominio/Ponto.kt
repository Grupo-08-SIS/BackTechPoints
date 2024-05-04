package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "ponto")
class Ponto(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_ponto")
        var idPonto: Int,

        @Column(name = "qtd_ponto")
        var qtdPonto: Int,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "fk_tipo_ponto", referencedColumnName = "id_tipo_ponto")
        var tipoPonto: TipoPonto? = null

) {
}