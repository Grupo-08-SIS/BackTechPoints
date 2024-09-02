package techForAll.techPoints.dtos

import techForAll.techPoints.domain.Curso


class AlunoDto(

    val id: Long,

    val primeiroNome: String,

    val sobrenome: String,

    val email: String,

    val cursos: List<Curso>,

    val emblemas: List<PontosPorCursoDto> = emptyList(),

    val cidade: String,

    val escolaridade: String,

    val descricao: String,

    val pontosSemanaAtual: Int,

    val pontosSemanaPassada: Int

)