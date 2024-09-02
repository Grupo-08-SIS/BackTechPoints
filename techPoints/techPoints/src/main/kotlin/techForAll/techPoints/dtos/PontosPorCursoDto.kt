package techForAll.techPoints.dtos

import techForAll.techPoints.domain.Curso


class PontosPorCursoDto (

    val idPontos: Int,

    val curso: Curso,

    val totalPontos: Int
)