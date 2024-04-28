package TechForAll.techPoints.dominio

import java.time.LocalDateTime

data class UsuarioDTO(
    val idUsuario: Int,
    val nomeUsuario: String,
    val cpf: String?,
    val primeiroNome: String?,
    val sobrenome: String?,
    val email: String,
    val dataCriacao: LocalDateTime?,
    val deletado: Boolean,
    val dataDeletado: LocalDateTime?,
    val dataAtualizacao: LocalDateTime?,
    val endereco: Endereco?
)