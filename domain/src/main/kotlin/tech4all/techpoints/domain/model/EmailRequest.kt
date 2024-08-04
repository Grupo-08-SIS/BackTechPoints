package tech4all.techpoints.domain.model

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class EmailRequest(

    val email: String
)
