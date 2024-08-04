package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.PontosPorCursoAoMes

interface GraficoColunaService {

    fun findPontosPorCursoAoMes(idUsuario: Int): List<PontosPorCursoAoMes>

}
