package TechForAll.techPoints.dto

import java.time.LocalDateTime

data class UsuarioDTOInput(
        val idUsuario: Int?,
        val nomeUsuario: String,
        val cpf: String?,
        val senha: String,
        val primeiroNome: String?,
        val sobrenome: String?,
        val email: String,
        val autenticado: Boolean?,
        val dataCriacao: LocalDateTime?,
        val deletado: Boolean,
        val dataDeletado: LocalDateTime?,
        val dataAtualizacao: LocalDateTime?,
        val idEndereco: Int,
        val idTipo: Int,
        val telefone: String?,
)