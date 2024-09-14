package techForAll.techPoints.repository

import techForAll.techPoints.domain.DadosEmpresa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface DadosEmpresaRepository : JpaRepository<DadosEmpresa, Long> {
    fun findByCnpj(cnpj: String): Optional<DadosEmpresa>
}