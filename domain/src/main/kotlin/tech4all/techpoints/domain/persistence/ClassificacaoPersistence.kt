package tech4all.techpoints.domain.persistence

import tech4all.techpoints.domain.model.Classificacao
import tech4all.techpoints.domain.model.PontosPorCurso

interface ClassificacaoPersistence {

    fun findClassificacao(cursoId: Int?): List<Classificacao>

    fun findPontosPorCurso(idUsuario: Int): List<PontosPorCurso>

}
