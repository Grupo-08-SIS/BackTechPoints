package techForAll.techPoints.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Pontuacao
import techForAll.techPoints.dtos.PontuacaoComPontosDTO
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.PontuacaoRepository

@Service
class PontuacaoService @Autowired constructor(
    private val pontuacaoRepository: PontuacaoRepository,
    private val alunoRepository: AlunoRepository
) {

    fun recuperarTodosCursosAlunoPontuacao(idAluno: Long):  Map<Long, List<PontuacaoComPontosDTO>>  {

        val aluno = alunoRepository.findById(idAluno).orElseThrow();
        val atividades = pontuacaoRepository.findByAlunoOrderByCurso(aluno);
        val atividadesPontos = atividades.map { pontuacao ->
            val pontos = pontuacao.getPontosAtividade();
            PontuacaoComPontosDTO(
                id = pontuacao.id,
                dataEntrega = pontuacao.dataEntrega,
                nomeAtividade = pontuacao.nomeAtividade,
                notaAtividade = pontuacao.notaAtividade,
                notaAluno = pontuacao.notaAluno,
                pontosAtividade = pontos,
                cursoId = pontuacao.curso.id,
                alunoId = pontuacao.aluno.id
            )
        }

        return atividadesPontos.groupBy { it.cursoId };
    }
}