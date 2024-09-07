package techForAll.techPoints.dtos

import techForAll.techPoints.domain.Curso


class AlunoDto(

    var id: Long,

    var primeiroNome: String,

    var sobrenome: String,

    var email: String,

    var cursos: List<Curso>,

    var emblemas: List<PontosPorCursoDto> = emptyList(),

    var cidade: String,

    var escolaridade: String,

    var descricao: String,

    var pontosSemanaAtual: Int,

    var pontosSemanaPassada: Int

)