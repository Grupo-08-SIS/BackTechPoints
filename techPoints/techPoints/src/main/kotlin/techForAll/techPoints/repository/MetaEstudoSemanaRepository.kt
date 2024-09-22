package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import techForAll.techPoints.domain.MetaEstudoSemana
import java.util.Optional

interface MetaEstudoSemanaRepository: JpaRepository<MetaEstudoSemana, Long> {

    fun findByAlunoId(alunoId: Long): Optional<MetaEstudoSemana>
}