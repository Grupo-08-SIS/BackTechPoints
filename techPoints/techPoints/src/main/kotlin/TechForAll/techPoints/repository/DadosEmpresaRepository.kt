package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.DadosEmpresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DadosEmpresaRepository : JpaRepository<DadosEmpresa, Int> {

}