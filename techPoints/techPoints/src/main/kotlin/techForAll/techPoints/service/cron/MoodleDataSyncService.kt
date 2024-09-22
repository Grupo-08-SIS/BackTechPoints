package techForAll.techPoints.service.cron

import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import techForAll.techPoints.domain.MetaEstudoSemana
import techForAll.techPoints.domain.Pontuacao
import techForAll.techPoints.domain.TempoSessao
import techForAll.techPoints.repository.*
import techForAll.techPoints.service.MoodleService
import kotlin.jvm.optionals.getOrElse

@Component
class MoodleDataSyncService(
    val pontuacaoRepository: PontuacaoRepository,
    val tempoSessaoRepository: TempoSessaoRepository,
    val alunoRepository: AlunoRepository,
    val cursoRepository: CursoRepository,
    val metaEstudoSemana: MetaEstudoSemanaRepository,
    val moodleService: MoodleService
) {

    @Scheduled(cron = "0 0 0 * * *") // Executa todo dia à meia-noite
    @Transactional
    fun sincronizarDadosDoMoodle() {

        val moodlePontuacoes = moodleService.buscarPontuacoes();
        val moodleTemposSessao = moodleService.buscarTemposSessao();

        moodlePontuacoes.forEach { pontuacaoMoodle ->
            val aluno = alunoRepository.findByMoodleId(pontuacaoMoodle.alunoId);
            val curso = cursoRepository.findByNome(pontuacaoMoodle.cursoNome);

            try {
                val cursoEncontrado = curso.get();

                val pontuacao = Pontuacao(
                    id = 0L, // Deixe o JPA gerar o ID
                    dataEntrega = pontuacaoMoodle.dataEntrega,
                    nomeAtividade = pontuacaoMoodle.nomeAtividade,
                    notaAtividade = pontuacaoMoodle.notaAtividade,
                    notaAluno = pontuacaoMoodle.notaAluno,
                    curso = cursoEncontrado,
                    aluno = aluno
                );
                pontuacaoRepository.save(pontuacao);
            } catch (e: Exception) {
                TODO("Not yet implemented");
            }

        }

        moodleTemposSessao.forEach { tempoMoodle ->
            val aluno = alunoRepository.findByMoodleId(tempoMoodle.alunoId);
            val metaEstudoSemana = metaEstudoSemana.findByAlunoId(tempoMoodle.alunoId);

            try {

                val metaEncontrada = metaEstudoSemana.get();

                val tempoSessao = TempoSessao(
                    id = 0L,
                    diaSessao = tempoMoodle.diaSessao,
                    qtdTempoSessao = tempoMoodle.qtdTempoSessao,
                    aluno = aluno,
                    metaEstudoSemana = metaEncontrada
                );
                tempoSessaoRepository.save(tempoSessao);

            } catch (e: Exception) {
                TODO("Not yet implemented");
            }
        }
    }
}