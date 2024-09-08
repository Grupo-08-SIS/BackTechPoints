package techForAll.techPoints.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class DadosEmpresa(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var nomeEmpresa: String,

    @Column(nullable = false, unique = true)
    var cnpj: String,

    @Column(nullable = false)
    var setorIndustria: String,

    @Column(nullable = false)
    var telefoneContato: String,  // Campo para o telefone

    @Column(nullable = false, unique = true)
    var emailCorporativo: String,  // Campo para o email corporativo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    var endereco: Endereco,

    @Column(nullable = false)
    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    @OneToMany(mappedBy = "empresa", fetch = FetchType.LAZY)
    var recrutadores: List<Recrutador> = listOf()
) {
    @PrePersist
    fun onPrePersist() {
        dataCriacao = LocalDateTime.now()
    }

    @PreUpdate
    fun onPreUpdate() {
        dataAtualizacao = LocalDateTime.now()
    }
}