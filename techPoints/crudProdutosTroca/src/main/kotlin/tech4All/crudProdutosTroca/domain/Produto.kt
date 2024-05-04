package tech4All.crudProdutosTroca.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

data class Produto (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

    @field:Size(max = 45)
    @field:NotBlank
    var nome:String,

    @field:NotNull
    @field:Positive
    var valorPontos:Double,

    @field:Size(max = 100)
    var descricao:String?,

    @field:Positive
    @field:NotNull
    var quantidade:Int,

    @field:NotNull
    var disponivel:Boolean,

    @field:ManyToOne
    @field:NotBlank
    var categoriaProduto: CategoriaProduto
)