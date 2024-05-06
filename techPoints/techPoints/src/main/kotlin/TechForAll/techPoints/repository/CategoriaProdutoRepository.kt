package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.CategoriaProduto
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaProdutoRepository : JpaRepository<CategoriaProduto, Int> {

}