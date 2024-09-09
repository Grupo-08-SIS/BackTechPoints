package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Curso

@Repository
interface AlunoRepository : JpaRepository<Aluno, Long> {
}