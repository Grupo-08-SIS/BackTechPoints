package tech4All.crudProdutosTroca.domain

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class Carrinho (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

    @field:NotBlank
    @field:ManyToOne
    var produto: Produto,

    @field:NotBlank
    @field:ManyToOne
    var usuario: Usuario,

    @field:NotNull
    @field:Positive
    var quantidadeProduto: Int
)