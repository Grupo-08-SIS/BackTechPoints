package techForAll.techPoints.moodle

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import techForAll.techPoints.config.MoodleJdbcTemplateConfig
import techForAll.techPoints.domain.Curso
import techForAll.techPoints.domain.Pontuacao
import techForAll.techPoints.domain.TempoSessao
import techForAll.techPoints.dtos.CursoMoodleDto
import techForAll.techPoints.dtos.PontuacaoMoodleDto
import techForAll.techPoints.dtos.TempoSessaoMoodleDto
import techForAll.techPoints.repository.*
import javax.sql.DataSource

@Service
class MoodleService(
    moodleJdbcTemplateConfig: MoodleJdbcTemplateConfig,
) {

    //TODO: JOGAR TUDO DE JPA PARA DATA SYNC, COMO ESTAVA MISTURADO ELE ESTÁ MORRENDO

    @Autowired
    private val jdbcTemplate = moodleJdbcTemplateConfig.moodleJdbcTemplate();

    fun buscarECadastrarCursos() {

        println("oxi")
        val sql = """
        SELECT
            mdl_course.id AS id_curso,
            mdl_course.fullname AS nome_curso,
            COUNT(atividade.id) AS total_atividades,
            COUNT(DISTINCT nota.userid) AS total_alunos_com_notas
        FROM
            mdl_course
        JOIN
            mdl_grade_items atividade ON atividade.courseid = mdl_course.id
        LEFT JOIN
            mdl_grade_grades nota ON nota.itemid = atividade.id
        GROUP BY
            mdl_course.id, mdl_course.fullname;
    """

        val cursos = jdbcTemplate.query(sql) { rs, _ ->
            CursoMoodleDto(
                id = rs.getLong("id_curso"),
                nome = rs.getString("nome_curso"),
                totalAtividades = rs.getInt("total_atividades"),
                totalAtividadesDoAluno = rs.getInt("total_alunos_com_notas")
            )
        }

        cursos.forEach { cursoDTO ->
            // Mapeie o DTO para a entidade antes de salvar.
            val curso = Curso(
                id = cursoDTO.id,
                nome = cursoDTO.nome,
                totalAtividades = cursoDTO.totalAtividades,
                totalAtividadesDoAluno = cursoDTO.totalAtividadesDoAluno
            )
//            cadastrarCurso(curso)
        }

    }

    fun buscarPontuacoes(): List<PontuacaoMoodleDto> {
        val sql = """
        SELECT 
            aluno.id AS aluno_id, 
            aluno.email AS aluno_email,
            curso.fullname AS curso_nome,
            FROM_UNIXTIME(nota.timemodified) AS data_entrega, 
            item.itemname AS nome_atividade,
            nota.rawgrade AS nota_atividade, 
            nota.finalgrade AS nota_aluno
        FROM 
            mdl_grade_grades nota
        JOIN 
            mdl_user aluno ON aluno.id = nota.userid
        JOIN 
            mdl_grade_items item ON item.id = nota.itemid
        JOIN 
            mdl_course curso ON curso.id = item.courseid;
    """
        return jdbcTemplate.query(sql) { rs, _ ->
            val dataEntrega = rs.getString("data_entrega")
            val nomeAtividade = rs.getString("nome_atividade")
            val notaAtividade = rs.getDouble("nota_atividade")
            val notaAluno = rs.getDouble("nota_aluno")

            // Verifica se a entrega foi realizada
            if (dataEntrega == null || nomeAtividade == null || notaAtividade == null || notaAluno == null) {
                // Retorna um objeto com informações sobre a não entrega
                return@query PontuacaoMoodleDto(
                    alunoId = rs.getLong("aluno_id"),
                    alunoEmail = rs.getString("aluno_email"),
                    cursoNome = rs.getString("curso_nome"),
                    dataEntrega = "Não entregue", // ou qualquer mensagem que faça sentido
                    nomeAtividade = nomeAtividade ?: "Atividade não informada",
                    notaAtividade = notaAtividade,
                    notaAluno = notaAluno
                )
            }

            // Caso contrário, retorna a pontuação normal
            PontuacaoMoodleDto(
                alunoId = rs.getLong("aluno_id"),
                alunoEmail = rs.getString("aluno_email"),
                cursoNome = rs.getString("curso_nome"),
                dataEntrega = dataEntrega,
                nomeAtividade = nomeAtividade,
                notaAtividade = notaAtividade,
                notaAluno = notaAluno
            )
        }
    }


    fun buscarTemposSessao(): List<TempoSessaoMoodleDto> {
        val sql = """
            SELECT 
                aluno.id AS aluno_id, 
                aluno.email AS aluno_email, 
                FROM_UNIXTIME(sessao.timecreated) AS dia_sessao, 
                (sessao.timemodified - sessao.timecreated) / 60 AS qtd_tempo_sessao -- duração em minutos
            FROM 
                mdl_sessions sessao
            JOIN 
                mdl_user aluno ON aluno.id = sessao.userid;
        """
        return jdbcTemplate.query(sql) { rs, _ ->
            TempoSessaoMoodleDto(
                alunoId = rs.getLong("aluno_id"),
                alunoEmail = rs.getString("aluno_email"),
                diaSessao = rs.getString("dia_sessao"),
                qtdTempoSessao = rs.getDouble("qtd_tempo_sessao")
            )
        }
    }

    @Transactional
//    fun cadastrarCurso(curso: Curso) {
//        cursoRepository.save(curso)
//    }
//
//    @Transactional
//    fun cadastrarPontuacao(pontuacao: Pontuacao) {
//        pontuacaoRepository.save(pontuacao)
//    }
//
//    @Transactional
//    fun cadastrarTempoSessao(tempoSessao: TempoSessao) {
//        tempoSessaoRepository.save(tempoSessao)
//    }


    fun atualizarDadosNoBanco() {
        buscarECadastrarCursos()

        val pontuacoes = buscarPontuacoes()
//        val pontuacaoConvertido = pontuacoes.map { converterParaPontuacao(it) }
//        pontuacaoConvertido.forEach { cadastrarPontuacao(it) }

        val temposSessoes = buscarTemposSessao()
//        val tempoSessaoConvertido =  temposSessoes.map { converterParaTempoSessao(it) };
//        tempoSessaoConvertido.forEach { cadastrarTempoSessao(it) }
    }

//    private fun converterParaTempoSessao(tempoSessaoDto: TempoSessaoMoodleDto): TempoSessao {
//        val aluno = alunoRepository.findById(tempoSessaoDto.alunoId).orElseThrow {
//            IllegalArgumentException("Aluno não encontrado")
//        }
//
//        val metaEstudoSemana = metaEstudoSemanaRepository.findById(1L).orElseThrow {
//            IllegalArgumentException("Meta de estudo da semana não encontrada")
//        }
//
//        return TempoSessao(
//            id = 0,
//            diaSessao = tempoSessaoDto.diaSessao,
//            qtdTempoSessao = tempoSessaoDto.qtdTempoSessao,
//            aluno = aluno,
//            metaEstudoSemana = metaEstudoSemana
//        )
//    }
//
//    private fun converterParaPontuacao(pontuacaoDto: PontuacaoMoodleDto): Pontuacao {
//        val aluno = alunoRepository.findById(pontuacaoDto.alunoId).orElseThrow {
//            IllegalArgumentException("Aluno não encontrado")
//        }
//
//        val curso = cursoRepository.findByNome(pontuacaoDto.cursoNome).orElseThrow {
//            IllegalArgumentException("Curso não encontrado") // Ajuste conforme necessário para buscar pelo ID
//        }
//
//        return Pontuacao(
//            id = 0,
//            dataEntrega = pontuacaoDto.dataEntrega,
//            nomeAtividade = pontuacaoDto.nomeAtividade,
//            notaAtividade = pontuacaoDto.notaAtividade,
//            notaAluno = pontuacaoDto.notaAluno,
//            aluno = aluno,
//            curso = curso
//        )
//    }

}
