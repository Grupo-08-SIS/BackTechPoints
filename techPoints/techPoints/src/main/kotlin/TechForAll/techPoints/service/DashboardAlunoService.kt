package TechForAll.techPoints.service

import TechForAll.techPoints.dto.*
import TechForAll.techPoints.repository.*
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate

@Service
class DashboardAlunoService(
    private val graficoColunaRepository: GraficoColunaRepository,
    private val graficoLinhaRepository: GraficoLinhaRepository,
    private val pontosSemanaRepository: PontosSemanaRepository,
    private val classificacaoRepository: ClassificacaoRepository,
    private val atividadesUsuarioRepository: AtividadesUsuarioRepository
) {

    fun getPontosAoLongoDoTempo(idUsuario: Int): List<Any> {
        val results = graficoLinhaRepository.findPontosPorDiaECurso(idUsuario)
        return results.map { result ->
            PontosAoLongoDoTempoDTO(
                nome = result[0] as String,
                data_pontuacao = (result[1] as java.sql.Date).toString(),
                pontos = (result[2] as Number).toInt(),
                usuario = (result[3] as Number).toInt()
            )
        }
    }


    fun getPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO> {
        val results = graficoColunaRepository.findPontosPorCursoAoMes(idUsuario)
        return results.map { result ->
            PontosPorCursoAoMesDTO(
                mes = result[0] as String,
                nome = result[1] as String,
                pontos = (result[2] as Long).toInt()
            )
        }
    }

    fun getPontosSemana(idUsuario: Int): PontosSemanaDTO {
        val result = pontosSemanaRepository.findPontosSemana(idUsuario).firstOrNull()
            ?: throw NoSuchElementException("Sem registros deste usuario ou ID não encontrado")

        return PontosSemanaDTO(
            idUsuario = result[0] as Int,
            nomeUsuario = result[1] as String,
            pontosSemanaAtual = (result[2] as Number).toInt(),
            pontosSemanaPassada = (result[3] as Number).toInt(),
            diferencaPontos = (result[4] as Number).toInt()
        )
    }

    fun getAtividadesDoUsuario(idUsuario: Int): List<AtividadesUsuarioDTO> {
        val totalAtividadesPorCurso: List<Array<Any>> = atividadesUsuarioRepository.findTotalAtividadesPorCurso()
        val atividadesPorCursoEUsuario: List<Array<Any>> = atividadesUsuarioRepository.findAtividadesPorCursoEUsuario(idUsuario)

        val totalAtividadesMap = totalAtividadesPorCurso.associate { it[0] as Number to it[1] as Number }

        return atividadesPorCursoEUsuario.mapNotNull { atividade ->
            val idCurso = (atividade[0] as Number).toInt()
            val nomeCurso = atividade[1] as String
            val totalAtividadesUsuario = (atividade[2] as Number).toInt()
            val totalQtdAtividades = totalAtividadesMap[idCurso]?.toInt()

            if (totalQtdAtividades != null) {
                AtividadesUsuarioDTO(
                    idCurso = idCurso,
                    nomeCurso = nomeCurso,
                    totalQtdAtividades = totalQtdAtividades,
                    totalAtividadesUsuario = totalAtividadesUsuario
                )
            } else null
        }
    }

    fun buscarClassificacao(cursoId: Int?) : List<ClassificacaoDTO>{

        val listaAlunos: List<Array<Any>> = classificacaoRepository.findClassificacao(cursoId)

        return listaAlunos.map { row ->
            ClassificacaoDTO(
                idUsuario = row[0] as Int,
                nomeUsuario = row[1] as String,
                email = row[2] as String,
                totalPontos = row[3] as Int
            )
        }
    }
}
