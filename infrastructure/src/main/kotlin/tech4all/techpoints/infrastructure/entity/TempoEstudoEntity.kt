package tech4all.techpoints.infrastructure.entity

import jakarta.persistence.*


@Entity
@Table(name = "tempo_estudo")
public data class TempoEstudoEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tempo_estudo")
    var idTempoEstudo: Int,

    @Column(name = "nome_dia")
    var nomeDia: String,

    @Column(name = "qtd_tempo_estudo")
    var qtdTempoEstudo: String,

    @Column(name = "ativado")
    var ativado: Boolean,

    @Column(name = "meta_alcancada")
    var metaAtingida: Boolean,


)
