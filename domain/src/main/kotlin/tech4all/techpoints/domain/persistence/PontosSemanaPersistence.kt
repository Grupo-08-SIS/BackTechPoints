package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.PontosSemana
import java.util.Optional

interface PontosSemanaPersistence {

    fun findPontosSemana(idUsuario: Int): List<PontosSemana>

}
