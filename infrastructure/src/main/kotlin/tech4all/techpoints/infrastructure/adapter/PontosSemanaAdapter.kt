package tech4all.techpoints.infrastructure.adapter

import org.springframework.stereotype.Component
import tech4all.techpoints.infrastructure.dto.PontosSemanaDTO
import tech4all.techpoints.infrastructure.repository.PontosSemanaRepository

@Component
class PontosSemanaAdapter(
    private val pontosSemanaRepository: PontosSemanaRepository
) {

    fun findPontosSemana(idUsuario: Int): List<PontosSemanaDTO> {
        return pontosSemanaRepository.findPontosSemana(idUsuario)
    }
}
