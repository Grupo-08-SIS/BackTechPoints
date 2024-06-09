package TechForAll.techPoints.dto

data class AlunoDTO (
    val idUsuario: Int,
    val nomeUsuario: String,
    val primeiroNome: String,
    val sobrenome: String,
    val email: String,
    val nomeCurso: String,
    val cidade: String
)