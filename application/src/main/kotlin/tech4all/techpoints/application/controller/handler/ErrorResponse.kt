package tech4all.techpoints.application.controller.handler

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

data class ErrorResponse @JvmOverloads constructor(
    @field:Schema(description = "Um identificador único da requisição no sistema de rastreamento") private val traceId: String,
    @field:Schema(
        description = "Causa detalhada do erro"
    ) private val details: String? = null
) {
    @Schema(description = "Hora em que o erro ocorreu", pattern = "yyyy-MM-ddThh:mm:ss.SSSSSSS")
    private val timestamp: LocalDateTime = LocalDateTime.now()
}
