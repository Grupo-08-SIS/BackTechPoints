package TechForAll.techPoints.repository

import TechForAll.techPoints.dominio.DadosEmpresa
import org.springframework.data.jpa.repository.JpaRepository

interface DadosEmpresaRepository : JpaRepository<DadosEmpresa, Int> {

}