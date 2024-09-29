package techForAll.techPoints.moodle

import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import techForAll.techPoints.domain.Pontuacao
import techForAll.techPoints.domain.TempoSessao
import techForAll.techPoints.repository.*

@Component
class MoodleDataSyncService(
    val pontuacaoRepository: PontuacaoRepository,
    val tempoSessaoRepository: TempoSessaoRepository,
    val alunoRepository: AlunoRepository,
    val cursoRepository: CursoRepository,
    val metaEstudoSemana: MetaEstudoSemanaRepository,
    val moodleService: MoodleService
) {

    // @Scheduled(cron = "0 0 0 * * *") // Executa todo dia à meia-noite
    @Scheduled(cron = "*/10 * * * * *") // Para testar -> 10 segundos
    @Transactional
    fun sincronizarDadosDoMoodle() {

        moodleService.atualizarDadosNoBanco();

//        val moodlePontuacoes = moodleService.buscarPontuacoes();
//        val moodleTemposSessao = moodleService.buscarTemposSessao();
//
//        moodlePontuacoes.forEach { pontuacaoMoodle ->
//            val aluno = alunoRepository.findByEmail(pontuacaoMoodle.alunoEmail);
//            val curso = cursoRepository.findByNome(pontuacaoMoodle.cursoNome);
//
//            try {
//                val cursoEncontrado = curso.get();
//                if (aluno.isEmpty) {
//                    TODO("THROW EXCEPTION ALUNO NOT FOUND");
//                }
//
//                val alunoEncontrado = aluno.get();
//                val pontuacao = Pontuacao(
//                    id = 0L, // Deixe o JPA gerar o ID
//                    dataEntrega = pontuacaoMoodle.dataEntrega,
//                    nomeAtividade = pontuacaoMoodle.nomeAtividade,
//                    notaAtividade = pontuacaoMoodle.notaAtividade,
//                    notaAluno = pontuacaoMoodle.notaAluno,
//                    curso = cursoEncontrado,
//                    aluno = alunoEncontrado
//                );
//                pontuacaoRepository.save(pontuacao);
//            } catch (e: Exception) {
//                TODO("Not yet implemented");
//            }
//
//        }
//
//        moodleTemposSessao.forEach { tempoMoodle ->
//            val aluno = alunoRepository.findByEmail(tempoMoodle.alunoEmail);
//            val metaEstudoSemana = metaEstudoSemana.findByAlunoEmail(tempoMoodle.alunoEmail);
//
//            try {
//
//                val metaEncontrada = metaEstudoSemana.get();
//                if (aluno.isEmpty) {
//                    TODO("THROW EXCEPTION ALUNO NOT FOUND");
//                }
//
//                val alunoEncontrado = aluno.get();
//
//                val tempoSessao = TempoSessao(
//                    id = 0L,
//                    diaSessao = tempoMoodle.diaSessao,
//                    qtdTempoSessao = tempoMoodle.qtdTempoSessao,
//                    aluno = alunoEncontrado,
//                    metaEstudoSemana = metaEncontrada
//                );
//                tempoSessaoRepository.save(tempoSessao);
//
//            } catch (e: Exception) {
//                TODO("Not yet implemented");
//            }
//        }
    }
}