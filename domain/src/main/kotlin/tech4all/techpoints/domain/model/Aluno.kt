package tech4all.techpoints.domain.model

data class Aluno(
    val id: Int,
    val nomeUsuario: String,
    val primeiroNome: String?,
    val sobrenome: String?,
    val email: String,
    val nomeCurso: String?,
    val cidade: String?
)
