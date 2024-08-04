package tech4all.techpoints.application.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailRequest (
    @field:NotBlank(message = "O email não pode estar em branco")
    @field:Email(message = "O email deve ser válido")
    val email: String
)