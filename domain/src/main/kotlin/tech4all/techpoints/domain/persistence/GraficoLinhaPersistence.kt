package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.PontosPorDiaECurso
import java.util.Optional

interface GraficoLinhaPersistence {

    fun findPontosPorDiaECurso(idUsuario: Int): List<PontosPorDiaECurso>

}
