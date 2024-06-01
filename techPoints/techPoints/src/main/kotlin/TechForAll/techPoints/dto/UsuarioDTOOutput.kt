package TechForAll.techPoints.dto

import TechForAll.techPoints.dominio.Endereco
import java.time.LocalDateTime

data class UsuarioDTOOutput(
    val idUsuario: Int,
    val nomeUsuario: String,
    val cpf: String?,
    val primeiroNome: String?,
    val sobrenome: String?,
    val email: String,
    val autenticado: Boolean?,
    val dataCriacao: LocalDateTime?,
    val deletado: Boolean,
    val dataDeletado: LocalDateTime?,
    val dataAtualizacao: LocalDateTime?,
    val endereco: Endereco
)