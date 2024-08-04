package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.Classificacao
import tech4all.techpoints.domain.model.PontosPorCurso

interface ClassificacaoService {

    fun findClassificacao(cursoId: Int?): List<Classificacao>

    fun findPontosPorCurso(idUsuario: Int): List<PontosPorCurso>

}
