package tech4all.techpoints.domain.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class Usuario(

    var idUsuario: Int,

    var nomeUsuario: String,

    var cpf: String,

    var senha: String,

    var primeiroNome: String?,

    var sobrenome: String?,

    var email: String,

    var autenticado: Boolean?,

    var imagemPerfil: ByteArray?,

    var dataCriacao: LocalDateTime = LocalDateTime.now(),

    var deletado: Boolean = false,

    var dataDeletado: LocalDateTime?,

    var dataAtualizacao: LocalDateTime = LocalDateTime.now(),

    var endereco: Endereco?,

    var tipoUsuario: TipoUsuario?,

    val inscricoes: List<Inscricao> = ArrayList()
)