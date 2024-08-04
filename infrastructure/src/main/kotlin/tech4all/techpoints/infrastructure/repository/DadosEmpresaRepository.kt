package tech4all.techpoints.infrastructure.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tech4all.techpoints.infrastructure.entity.DadosEmpresaEntity

@Repository
interface DadosEmpresaRepository : JpaRepository<DadosEmpresaEntity, Int> {

}