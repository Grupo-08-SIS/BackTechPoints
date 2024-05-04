package tech4All.crudProdutosTroca.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

@Entity
data class CategoriaProduto (
    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,

    @field: Size(max = 45)
    @field: NotBlank
    @field: NotNull
    var nome:String,

    @field: Size(max = 100)
    var descricaoProduto: String?



)