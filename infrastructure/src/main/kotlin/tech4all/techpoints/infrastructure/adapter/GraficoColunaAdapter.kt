package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.PontosPorCursoAoMesDTO
import tech4all.techpoints.infrastructure.repository.GraficoColunaRepository

@Component
class GraficoColunaAdapter(
    private val graficoColunaRepository: GraficoColunaRepository
) {

    fun findPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMesDTO> {
        return graficoColunaRepository.findPontosPorCursoAoMes(idUsuario)
    }
}
