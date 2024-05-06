package tech4All.crudProdutosTroca.domain

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

@Entity
data class Produto (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Int?,

    @field:Size(max = 45)
    @field:NotBlank
    var nome:String?= null,

    @field:NotNull
    @field:Positive
    var valorPontos:Int? = null,

    @field:Size(max = 100)
    var descricao:String? = null,

    @field:Positive
    @field:NotNull
    var quantidade:Int? = null,

    @field:NotNull
    var disponivel:Boolean? = null,

    @field:ManyToOne
    var categoriaProduto: CategoriaProduto? = null
) {
constructor(
    paramId: Int,
    paramCategoriaProduto:CategoriaProduto
):
this(id = paramId, categoriaProduto = paramCategoriaProduto)
}