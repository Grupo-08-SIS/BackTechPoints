package techForAll.techPoints.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.dtos.PontuacaoComPontosDTO
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.PontuacaoRepository
import java.time.DayOfWeek
import java.time.LocalDate


@Service
class PontuacaoService @Autowired constructor(
    private val pontuacaoRepository: PontuacaoRepository,
    private val alunoRepository: AlunoRepository
) {

    fun alunoExiste(idAluno: Long): Aluno {

        return alunoRepository.findById(idAluno).orElseThrow();
    }

    fun recuperarTodosCursosAlunoPontuacao(idAluno: Long): Map<Long, List<PontuacaoComPontosDTO>> {

        val aluno = alunoExiste(idAluno);
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
                cursoNome = pontuacao.curso.nome,
                alunoId = pontuacao.aluno.id
            )
        }

        return atividadesPontos.groupBy { it.cursoId };
    }

    fun recuperarKPISemanaPassadaAndAtual(idAluno: Long): Map<String, Map<Long, Int>> {

        val alunoAgrupadoCurso = recuperarTodosCursosAlunoPontuacao(idAluno);
        val agora = LocalDate.now()
        val inicioSemanaAtual = agora.with(DayOfWeek.MONDAY)
        val inicioSemanaPassada = inicioSemanaAtual.minusWeeks(1)

        val pontosSemanaAtual = alunoAgrupadoCurso.mapValues { entrada ->
            entrada.value.filter { atividade ->
                val dataEntrega = LocalDate.parse(atividade.dataEntrega)
                !dataEntrega.isBefore(inicioSemanaAtual)
            }.sumOf { it.pontosAtividade };
        }

        val pontosSemanaPassada = alunoAgrupadoCurso.mapValues { entrada ->
            entrada.value.filter { atividade ->
                val dataEntrega = LocalDate.parse(atividade.dataEntrega)
                !dataEntrega.isAfter(inicioSemanaPassada) && dataEntrega.isBefore(inicioSemanaAtual)
            }.sumOf { it.pontosAtividade }
        }

        return mapOf("semanaPassada" to pontosSemanaPassada,"semanaAtual" to pontosSemanaAtual)
    }

    fun recuperarKPIEntregas(idAluno: Long): Map<String, Int> {

        val alunoAgrupadoCurso = recuperarTodosCursosAlunoPontuacao(idAluno);

        val atividadesEntregues: Int = alunoAgrupadoCurso.values
            .flatten()
            .count { true }

        val atividadesTotais: Int = alunoAgrupadoCurso.values
            .flatten()
            .count()

        val diferenca = atividadesTotais - atividadesEntregues

        return mapOf("atividadesTotais" to atividadesTotais,"atividadesNaoEntregues" to diferenca)

    }

//    fun recuperarAlunoCursoEspecifico(idAluno: Long, idCurso: Long): PontuacaoComPontosDTO {
//
//        val aluno = alunoRepository.findById(idAluno);
//
//      if (aluno.isPresent) {
//            val alunoPresente = aluno.get();
//            if (alunoPresente.cursos?.isNotEmpty() == true) {
//                alunoPresente.cursos = alunoPresente.cursos!!.filter { it.id == idCurso };
//
//               for (i in 1..alunoPresente.cursos!![0].totalAtividadesDoAluno){
//
//               }
//
//            } else {
//                throw Exception()
//            }
//        } else {
//            throw Exception()
//        }
//
//
//    }

    // ALUNO:
    // TODO: Atividades Entregues diferença de Atividades Totais
    // TODO: Soma total de Pontos do Curso
    // TODO: Recuperar Pontos de Atividades conquistados por dia e separados por Curso
    // TODO:Recuperar Pontos de Atividades conquistados por mês -> Geral e Filtro
    // TODO: Gráfico Radar <- Precisa de Banco Ainda
    // TODO: Meta de Estudo <- Próxima Sprint

    // RH
    // TODO: Todos os Alunos de um Curso, junto com sua pontuação -> Ter filtro de Idade, Município, Escolaridade e Curso
}