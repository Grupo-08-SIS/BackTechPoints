package tech4all.techpoints.domain.model

import jakarta.persistence.*
import tech4all.techpoints.domain.model.CategoriaCurso

data class Curso(

    var idCurso: Int,

    var nome: String,

    var qtdHoras: Int,

    var categoriaCurso: CategoriaCurso?,
)
