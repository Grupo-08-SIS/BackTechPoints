package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.PontosPorDiaECurso

interface GraficoLinhaService {

    fun findPontosPorDiaECurso(idUsuario: Int): List<PontosPorDiaECurso>

}
