package TechForAll.techPoints.dominio

import jakarta.persistence.*

@Entity
@Table(name = "referencia_gerar_pontuacao")
data class ReferenciaGerarPontuacao(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(name = "nome_da_geracao", length = 45)
    val nomeDaGeracao: String?,

    @Column(name = "descricao_da_geracao", length = 45)
    val descricaoDaGeracao: String?
)
