package tech4all.techpoints.infrastructure.dto

import tech4all.techpoints.domain.model.Endereco
import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime

data class UsuarioDTOOutput(
    val idUsuario: Int? = null,
    val nomeUsuario: String? = null,
    val cpf: String? = null,
    @JsonIgnore
    val senha: String? = null,
    val primeiroNome: String? = null,
    val sobrenome: String? = null,
    val email: String? = null ,
    @JsonIgnore
    var autenticado: Boolean? = null,
    val dataCriacao: LocalDateTime? = null,
    @JsonIgnore
    val deletado: Boolean? = null,
    @JsonIgnore
    val dataDeletado: LocalDateTime? = null,
    val dataAtualizacao: LocalDateTime? = null,
    val endereco: Endereco? = null
)
