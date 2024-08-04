package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.PontosPorDiaECursoDTO
import tech4all.techpoints.infrastructure.repository.GraficoLinhaRepository

@Component
class GraficoLinhaAdapter(
    private val graficoLinhaRepository: GraficoLinhaRepository
) {

    fun findPontosPorDiaECurso(idUsuario: Int): List<PontosPorDiaECursoDTO> {
        return graficoLinhaRepository.findPontosPorDiaECurso(idUsuario)
    }
}
