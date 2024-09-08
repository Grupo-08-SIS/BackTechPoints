package techForAll.techPoints.dominio

import jakarta.persistence.*
import techForAll.techPoints.domain.Aluno


@Entity
@Table(name = "curso")
data class CursoOld(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var idCurso: Int,

    @Column(name = "nome", length = 45, nullable = false)
    var nome: String,

    @Column(name = "qtd_horas", nullable = false)
    var qtdHoras: Int,

    @ManyToMany(mappedBy = "cursos", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val inscricoes: List<Aluno> = emptyList()
)
