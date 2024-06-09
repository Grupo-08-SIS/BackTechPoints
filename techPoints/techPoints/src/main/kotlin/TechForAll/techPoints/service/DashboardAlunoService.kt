package TechForAll.techPoints.service

import TechForAll.techPoints.dto.AtividadesUsuarioDTO
import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import TechForAll.techPoints.dto.PontosSemanaDTO
import TechForAll.techPoints.repository.*
import org.springframework.stereotype.Service
import java.sql.Date
import java.time.LocalDate

@Service
class DashboardAlunoService(
    private val graficoColunaRepository: GraficoColunaRepository,
    private val graficoLinhaRepository: GraficoLinhaRepository,
    private val pontosSemanaRepository: PontosSemanaRepository,
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

    fun getAtividadesUsuario(idUsuario: Int): List<AtividadesUsuarioDTO> {
        val results = atividadesUsuarioRepository.findAtividadesUsuario(idUsuario)
        return results.map { result ->
            AtividadesUsuarioDTO(
                idUsuario = (result[0] as Number).toInt(),
                nomeUsuario = result[1] as String,
                curso = result[2] as String,
                modulo = result[3] as String,
                totalAtividades = (result[4] as Number).toInt(),
                atividadesFeitas = (result[5] as Number).toInt()
            )
        }
    }

}
