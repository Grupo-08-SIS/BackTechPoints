package tech4All.crudProdutosTroca.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech4All.crudProdutosTroca.domain.CategoriaProduto

interface CategoriaProdutoRepository : JpaRepository<CategoriaProduto, Int> {

}