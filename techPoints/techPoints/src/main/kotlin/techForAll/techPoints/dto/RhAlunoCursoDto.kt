package techForAll.techPoints.dto

import techForAll.techPoints.dominio.Inscricao

data class RhAlunoCursoDto (
    val idCurso: Int,
    val nomeCurso: String,
    val idUsuario: Int,
    val nomeUsuario: String,
    val imagemPerfil: ByteArray,
    val email: String,
    val inscricoes: List<Inscricao>,
    val municipio: String

)