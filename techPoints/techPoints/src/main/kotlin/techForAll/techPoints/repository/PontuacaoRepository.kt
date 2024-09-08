package techForAll.techPoints.repository

import org.springframework.data.jpa.repository.JpaRepository
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Pontuacao

interface PontuacaoRepository : JpaRepository<Pontuacao, Long> {

    fun findByAlunoOrderByCurso(aluno: Aluno): List<Pontuacao>;
}