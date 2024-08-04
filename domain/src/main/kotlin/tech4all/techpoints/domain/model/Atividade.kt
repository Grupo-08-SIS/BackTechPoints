package tech4all.techpoints.domain.model

import jakarta.persistence.*
import java.util.*

data class Atividade(

    val id: Int,

    var nome: String,

    var descricao: String,

    val valor: Int?,

    val peso: Double?,

    val duracao: java.sql.Time?,

    val dataEntrega: Date?,

    val modulo: Modulo
)
