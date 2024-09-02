package techForAll.techPoints.repository

import techForAll.techPoints.domain.DadosEmpresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DadosEmpresaRepository : JpaRepository<DadosEmpresa, Int> {

}