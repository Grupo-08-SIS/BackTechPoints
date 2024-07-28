package TechForAll.techPoints.dto

import TechForAll.techPoints.dominio.Endereco
import TechForAll.techPoints.dominio.TipoUsuario
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
    val autenticado: Boolean? = null,
    val dataCriacao: LocalDateTime? = null,
    @JsonIgnore
    val deletado: Boolean? = null,
    @JsonIgnore
    val dataDeletado: LocalDateTime? = null,
    val dataAtualizacao: LocalDateTime? = null,
    val endereco: Endereco? = null,
        val tipoUsuario: Int? = null
)
