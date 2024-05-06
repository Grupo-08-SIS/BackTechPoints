package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.Produto
import org.springframework.data.jpa.repository.JpaRepository

interface ProdutoRepository : JpaRepository<Produto, Int> {
}