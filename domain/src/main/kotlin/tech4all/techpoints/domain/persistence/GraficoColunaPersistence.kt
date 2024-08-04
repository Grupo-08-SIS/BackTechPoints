package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.PontosPorCursoAoMes
import java.util.Optional

interface GraficoColunaPersistence {

    fun findPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMes>

}
