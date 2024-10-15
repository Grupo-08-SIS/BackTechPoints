package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import techForAll.techPoints.domain.MetaEstudoSemana

@Repository
interface MetaEstudoSemanaRepository : JpaRepository<MetaEstudoSemana, Long> {

    fun findByAlunoId(alunoId: Long): MetaEstudoSemana?
}