package techForAll.techPoints.moodle

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import techForAll.techPoints.dtos.PontuacaoMoodleDto
import techForAll.techPoints.dtos.TempoSessaoMoodleDto
import javax.sql.DataSource

@Service
class MoodleService(
    @Qualifier("moodleDataSource") moodleDataSource: DataSource
) {
    @Autowired
    private val jdbcTemplate = JdbcTemplate(moodleDataSource)

    fun buscarPontuacoes(): List<PontuacaoMoodleDto> {
        val sql = """
            SELECT nota.id, 
            nota.nota_atividade, 
            nota.data_entrega, 
            aluno.id AS aluno_id, 
            aluno.email AS aluno_email,
            curso.nome AS curso_nome
            FROM mdl_grade_grades nota
            JOIN mdl_user aluno ON aluno.id = nota.userid
            JOIN mdl_course curso ON curso.id = nota.courseid
        """
        return jdbcTemplate.query(sql) { rs, _ ->
            PontuacaoMoodleDto(
                alunoId = rs.getLong("aluno_id"),
                alunoEmail = rs.getString("aluno_email"),
                cursoNome = rs.getString("curso_nome"),
                dataEntrega = rs.getString("data_entrega"),
                nomeAtividade = rs.getString("nome_atividade"),
                notaAtividade = rs.getDouble("nota_atividade"),
                notaAluno = rs.getDouble("nota_aluno")
            )
        }
    }

    fun buscarTemposSessao(): List<TempoSessaoMoodleDto> {
        val sql = """
            SELECT sessao.aluno_id, sessao.aluno_email, sessao.dia_sessao, sessao.qtd_tempo
            FROM mdl_sessions sessao
            JOIN mdl_user aluno ON aluno.id = sessao.userid
        """
        return jdbcTemplate.query(sql) { rs, _ ->
            TempoSessaoMoodleDto(
                alunoId = rs.getLong("aluno_id"),
                alunoEmail = rs.getString("aluno_email"),
                diaSessao = rs.getString("dia_sessao"),
                qtdTempoSessao = rs.getDouble("qtd_tempo")
            )
        }
    }
}
