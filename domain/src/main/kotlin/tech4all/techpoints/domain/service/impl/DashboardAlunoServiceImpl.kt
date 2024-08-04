package tech4all.techpoints.domain.service.impl

import org.springframework.stereotype.Service
import tech4all.techpoints.domain.persistence.*
import tech4all.techpoints.domain.service.DashboardAlunoService
import tech4all.techpoints.domain.dto.*

import java.util.NoSuchElementException

@Service
class DashboardAlunoServiceImpl(
    private val graficoColunaPersistence: GraficoColunaPersistence,
    private val graficoLinhaPersistence: GraficoLinhaPersistence,
    private val pontosSemanaPersistence: PontosSemanaPersistence,
    private val classificacaoPersistence: ClassificacaoPersistence,
    private val atividadesUsuarioPersistence: AtividadesUsuarioPersistence
) : DashboardAlunoService {

    override fun getPontosAoLongoDoTempo(idUsuario: Int): List<PontosAoLongoDoTempoDTO> {
        val results = graficoLinhaPersistence.findPontosPorDiaECurso(idUsuario)
        return results.map { result ->
            PontosAoLongoDoTempoDTO(
                nome = result.nomeCurso,
                data_pontuacao = result.data.toString(),
                pontos = result.pontos,
                usuario = idUsuario
            )
        }
    }

    override fun getPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO> {
        val results = graficoColunaPersistence.findPontosPorCursoAoMes(idUsuario)
        return results.map { result ->
            PontosPorCursoAoMesDTO(
                mes = result.mes,
                idCurso = result.cursoId,
                nomeCurso = result.nomeCurso,
                totalPontos = result.totalPontos
            )
        }
    }

    override fun getPontosSemana(idUsuario: Int): PontosSemanaDTO {
        val result = pontosSemanaPersistence.findPontosSemana(idUsuario).firstOrNull()
            ?: throw NoSuchElementException("Sem registros deste usuario ou ID não encontrado")

        return PontosSemanaDTO(
            idUsuario = result.idUsuario,
            nomeUsuario = result.nomeUsuario,
            pontosSemanaAtual = result.pontosSemanaAtual,
            pontosSemanaPassada = result.pontosSemanaAnterior,
            diferencaPontos = result.variacaoPontos

        )
    }

    override fun getAtividadesDoUsuario(idUsuario: Int): List<AtividadesUsuarioDTO> {
        val totalAtividadesPorCurso = atividadesUsuarioPersistence.findTotalAtividadesPorCurso()
        val atividadesPorCursoEUsuario = atividadesUsuarioPersistence.findAtividadesPorCursoEUsuario(idUsuario)

        val totalAtividadesMap = totalAtividadesPorCurso.associate { it.cursoId to it.totalAtividades }

        return atividadesPorCursoEUsuario.mapNotNull { atividade ->
            val idCurso = atividade.cursoId
            val nomeCurso = atividade.nomeCurso
            val totalAtividadesUsuario = atividade.quantidadeAtividades
            val totalQtdAtividades = totalAtividadesMap[idCurso]

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

    override fun buscarClassificacao(cursoId: Int?): List<ClassificacaoDTO> {
        val listaAlunos = classificacaoPersistence.findClassificacao(cursoId)
        return listaAlunos.map { result ->
            ClassificacaoDTO(
                id = result.id,
                nomeUsuario = result.nomeUsuario,
                email = result.email,
                totalPontos = result.totalPontos,
                ranking = result.ranking
            )
        }
    }

    override fun buscarPontosPorCurso(idUsuario: Int): List<PontosPorCursoDTO> {
        val results = classificacaoPersistence.findPontosPorCurso(idUsuario)
        return results.map { result ->
            PontosPorCursoDTO(
                idCurso = result.cursoId,
                nome = result.nomeCurso,
                totalPontos = result.totalPontos
            )
        }
    }
}
