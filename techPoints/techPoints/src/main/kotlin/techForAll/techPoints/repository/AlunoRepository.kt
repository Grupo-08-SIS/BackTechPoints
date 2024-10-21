package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Curso
import java.util.*

@Repository
interface AlunoRepository : JpaRepository<Aluno, Long> {

    fun findByEmail(email: String): Optional<Aluno>;
  
    fun findByIdIn(ids: List<Any>): List<Aluno>

}