package tech4All.crudProdutosTroca.repository

import org.springframework.data.jpa.repository.JpaRepository
import tech4All.crudProdutosTroca.domain.Produto

interface ProdutoRepository : JpaRepository<Produto, Int> {
}