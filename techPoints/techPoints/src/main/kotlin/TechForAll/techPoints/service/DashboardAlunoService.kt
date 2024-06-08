package TechForAll.techPoints.service

import TechForAll.techPoints.dto.PontosAoLongoDoTempoDTO
import TechForAll.techPoints.dto.PontosPorCursoAoMesDTO
import TechForAll.techPoints.dto.PontosSemanaDTO
import TechForAll.techPoints.repository.PontuacaoRepository
import org.springframework.stereotype.Service

@Service
class DashboardAlunoService(private val pontuacaoRepository: PontuacaoRepository) {

    fun getPontosAoLongoDoTempo(idUsuario: Int): List<PontosAoLongoDoTempoDTO> {
        return pontuacaoRepository.findPontosAoLongoDoTempo(idUsuario)
    }

    fun getPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO> {
        return pontuacaoRepository.findPontosPorCursoAoMes(idUsuario)
    }

    fun getPontosSemana(idUsuario: Int): PontosSemanaDTO {
        return pontuacaoRepository.findPontosSemana(idUsuario)
    }
}