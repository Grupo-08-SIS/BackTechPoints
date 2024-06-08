package TechForAll.techPoints.service

import TechForAll.techPoints.dto.AtividadesUsuarioDTO
import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import TechForAll.techPoints.dto.PontosSemanaDTO
import TechForAll.techPoints.repository.*
import org.springframework.stereotype.Service

@Service
class DashboardAlunoService(
    private val graficoColunaRepository: GraficoColunaRepository,
    private val graficoLinhaRepository: GraficoLinhaRepository,
    private val pontosSemanaRepository: PontosSemanaRepository,
    private val atividadesUsuarioRepository: AtividadesUsuarioRepository
) {

    fun getPontosAoLongoDoTempo(idUsuario: Int): List<PontosAoLongoDoTempoDTO> {
        return graficoLinhaRepository.findPontosAoLongoDoTempo(idUsuario)
    }

    fun getPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO> {
        return graficoColunaRepository.findPontosPorCursoAoMes(idUsuario)
    }

    fun getPontosSemana(idUsuario: Int): PontosSemanaDTO {
        return pontosSemanaRepository.findPontosSemana(idUsuario)
    }

    fun getAtividadesUsuario(idUsuario: Int): List<AtividadesUsuarioDTO> {
        return atividadesUsuarioRepository.findAtividadesUsuario(idUsuario)
    }

}