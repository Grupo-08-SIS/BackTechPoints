package techForAll.techPoints.service

import jakarta.persistence.EntityManager

import jakarta.persistence.criteria.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import techForAll.techPoints.domain.Aluno
import techForAll.techPoints.domain.Curso
import techForAll.techPoints.domain.Endereco
import techForAll.techPoints.domain.Pontuacao
import techForAll.techPoints.dtos.AlunoDto
import techForAll.techPoints.dtos.PontuacaoComPontosDTO
import techForAll.techPoints.repository.AlunoRepository
import techForAll.techPoints.repository.PontuacaoRepository
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth


@Service
class PontuacaoService @Autowired constructor(
    private val entityManager: EntityManager,
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

            val notaAlunoCorrigida = pontuacao.notaAluno ?: 0.0
            val pontos = pontuacao.getPontosAtividade();

            PontuacaoComPontosDTO(
                id = pontuacao.id,
                dataEntrega = pontuacao.dataEntrega,
                nomeAtividade = pontuacao.nomeAtividade,
                notaAtividade = pontuacao.notaAtividade,
                notaAluno = notaAlunoCorrigida,
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
                dataEntrega.isAfter(inicioSemanaPassada) && dataEntrega.isBefore(inicioSemanaAtual)
            }.sumOf { it.pontosAtividade }
        }

        return mapOf("semanaPassada" to pontosSemanaPassada, "semanaAtual" to pontosSemanaAtual)
    }

    fun recuperarKPIEntregas(idAluno: Long): Map<String, Int> {

        val alunoAgrupadoCurso = recuperarTodosCursosAlunoPontuacao(idAluno);

        val atividadesEntregues: Int = alunoAgrupadoCurso.values
            .flatten()
            .count { atividade -> atividade.notaAluno != 0.0 }

        val atividadesTotais: Int = alunoAgrupadoCurso.values
            .flatten()
            .count()

        val diferenca = atividadesTotais - atividadesEntregues

        return mapOf(
            "atividadesTotais" to atividadesTotais,
            "atividadesEntregues" to atividadesEntregues,
            "atividadesNaoEntregues" to diferenca
        )

    }

    fun recuperarPontosConquistadosPorMes(idAluno: Long): Map<Pair<Long, String>, Map<YearMonth, Int>> {

        val alunoAgrupadoCurso = recuperarTodosCursosAlunoPontuacao(idAluno);

        return alunoAgrupadoCurso.mapKeys { entry ->
            val cursoId = entry.key
            val cursoNome = entry.value.firstOrNull()?.cursoNome ?: "Unknown"
            Pair(cursoId, cursoNome)
        }.mapValues { entry ->
            entry.value.groupBy { atividade ->
                YearMonth.from(LocalDate.parse(atividade.dataEntrega))
            }.mapValues { (_, atividades) ->
                atividades.sumOf { it.pontosAtividade }
            }
        }

    }

    fun recuperarPontosTotaisPorCurso(idAluno: Long): Map<Long, Map<String, Any>> {
        val alunoAgrupadoCurso = recuperarTodosCursosAlunoPontuacao(idAluno)

        return alunoAgrupadoCurso.mapValues { entry ->
            val cursoNome = entry.value.firstOrNull()?.cursoNome ?: "Unknown"
            val totalPontos = entry.value.sumOf { it.pontosAtividade }
            mapOf("nomeCurso" to cursoNome, "pontosTotais" to totalPontos)
        }
    }

    fun recuperarRankingPorCurso(): Map<Long, Map<String, Any>> {
        val todasPontuacoes = pontuacaoRepository.findAll()

        val pontuacoesAgrupadasPorCurso = todasPontuacoes.groupBy { it.curso.id }

        return pontuacoesAgrupadasPorCurso.mapValues { entry ->
            val cursoNome = entry.value.firstOrNull()?.curso?.nome ?: "Unknown"
            val ranking = entry.value.groupBy { it.aluno.id }
                .mapValues { alunoEntry -> alunoEntry.value.sumOf { it.getPontosAtividade() } }
                .toList()
                .sortedByDescending { it.second }
                .map { (alunoId, pontos) ->
                    val aluno = alunoRepository.findById(alunoId).orElseThrow { Exception("Aluno não encontrado") }
                    mapOf(
                        "aluno" to AlunoDto(
                            id = aluno.id,
                            primeiroNome = aluno.primeiroNome,
                            sobrenome = aluno.sobrenome,
                            email = aluno.email
                        ),
                        "pontosTotais" to pontos
                    )
                }
            mapOf(
                "nomeCurso" to cursoNome,
                "ranking" to ranking
            )
        }
    }


    fun recuperarRankingComFiltro(
        idade: Int?,
        escolaridade: String?,
        primeiroNome: String?,
        sobrenome: String?,
        cidade: String?,
        cursoId: Long?
    ): Map<Long, Map<String, Any>> {
        val criteriaBuilder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteriaQuery: CriteriaQuery<Pontuacao> = criteriaBuilder.createQuery(Pontuacao::class.java)
        val root: Root<Pontuacao> = criteriaQuery.from(Pontuacao::class.java)
        val predicates = mutableListOf<Predicate>()

        escolaridade?.let {
            predicates.add(
                criteriaBuilder.equal(
                    root.get<Aluno>("aluno").get<String>("escolaridade"),
                    it
                )
            )
        }
        cidade?.let {
            predicates.add(
                criteriaBuilder.equal(
                    root.get<Aluno>("aluno").get<Endereco>("endereco").get<String>("cidade"),
                    it
                )
            )
        }
        cursoId?.let { predicates.add(criteriaBuilder.equal(root.get<Curso>("curso").get<Long>("id"), it)) }

        if (primeiroNome != null && sobrenome != null) {
            predicates.add(
                criteriaBuilder.and(
                    criteriaBuilder.equal(root.get<Aluno>("aluno").get<String>("primeiro_nome"), primeiroNome),
                    criteriaBuilder.equal(root.get<Aluno>("aluno").get<String>("sobrenome"), sobrenome)
                )
            )
        }

        criteriaQuery.where(*predicates.toTypedArray())
        val query = entityManager.createQuery(criteriaQuery)
        val todasPontuacoes = query.resultList

        val pontuacoesAgrupadasPorCurso = todasPontuacoes.groupBy { it.curso.id }

        return pontuacoesAgrupadasPorCurso.mapValues { entry ->
            val cursoNome = entry.value.firstOrNull()?.curso?.nome ?: "Unknown"
            val ranking = entry.value.groupBy { it.aluno.id }
                .mapValues { alunoEntry -> alunoEntry.value.sumOf { it.getPontosAtividade() } }
                .toList()
                .sortedByDescending { it.second }
                .map { (alunoId, pontos) ->
                    val aluno = alunoRepository.findById(alunoId).orElseThrow { Exception("Aluno não encontrado") }
                    if (idade == null || aluno.calcularIdade() == idade) {
                        mapOf(
                            "aluno" to AlunoDto(
                                id = aluno.id,
                                primeiroNome = aluno.primeiroNome,
                                sobrenome = aluno.sobrenome,
                                email = aluno.email
                            ),
                            "pontosTotais" to pontos
                        )
                    } else {
                        null
                    }
                }.filterNotNull()
            mapOf(
                "nomeCurso" to cursoNome,
                "ranking" to ranking
            )
        }
    }
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
    // TODO: Soma total de Pontos do Curso
    // TODO: Gráfico Radar <- Precisa de Banco Ainda
    // TODO: Meta de Estudo <- Próxima Sprint

    // RH
    // TODO: Todos os Alunos de um Curso, junto com sua pontuação -> Ter filtro de Idade, Município, Escolaridade e Curso
