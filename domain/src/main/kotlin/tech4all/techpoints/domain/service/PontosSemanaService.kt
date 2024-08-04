package tech4all.techpoints.domain.service

import tech4all.techpoints.domain.model.PontosSemana

interface PontosSemanaService {

    fun findPontosSemana(idUsuario: Int): List<PontosSemana>

}
