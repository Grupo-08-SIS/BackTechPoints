package tech4all.techpoints.application.controller.handler

import io.opentelemetry.api.trace.Span
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import tech4all.techpoints.domain.exception.*

@RestControllerAdvice
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class ExceptionHandlerAdvice {

    private val log = LoggerFactory.getLogger(ExceptionHandlerAdvice::class.java)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFoundException(ex: NoSuchElementException): ErrorResponse? {
        log.error("method=handleNotFoundException | message: {}", ex.message)
        return traceId()?.let { ErrorResponse(it, ex.message) }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ErrorResponse? {
        log.error("method=handleValidationExceptions | message: {}", ex.message)
        val errors = ex.bindingResult.allErrors.joinToString(", ") {
            val fieldName = (it as FieldError).field
            val errorMessage = it.defaultMessage
            "$fieldName $errorMessage"
        }
        return traceId()?.let { ErrorResponse(it, errors) }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableExceptions(ex: HttpMessageNotReadableException): ErrorResponse? {
        log.error("method=handleHttpMessageNotReadableExceptions | message: {}", ex.message)
        return traceId()?.let { ErrorResponse(it, ex.message) }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyRegisteredException::class)
    fun handleEmailAlreadyRegisteredException(e: EmailAlreadyRegisteredException): ErrorResponse? {
        log.error("method=handleEmailAlreadyRegisteredException | message: {}", e.message)
        return traceId()?.let { ErrorResponse(it, e.message ?: "Email já cadastrado") }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsuarioNotFoundException::class)
    fun handleUsuarioNotFoundException(e: UsuarioNotFoundException): ErrorResponse? {
        log.error("method=handleUsuarioNotFoundException | message: {}", e.message)
        return traceId()?.let { ErrorResponse(it, e.message ?: "Usuário não encontrado") }
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentialsException(e: InvalidCredentialsException): ErrorResponse? {
        log.error("method=handleInvalidCredentialsException | message: {}", e.message)
        return traceId()?.let { ErrorResponse(it, e.message ?: "Credenciais inválidas") }
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(UnsupportedMediaTypeException::class)
    fun handleUnsupportedMediaTypeException(e: UnsupportedMediaTypeException): ErrorResponse? {
        log.error("method=handleUnsupportedMediaTypeException | message: {}", e.message)
        return traceId()?.let { ErrorResponse(it, e.message ?: "Tipo de mídia não suportado") }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestDataException::class)
    fun handleInvalidRequestDataException(e: InvalidRequestDataException): ErrorResponse? {
        log.error("method=handleInvalidRequestDataException | message: {}", e.message)
        return traceId()?.let { ErrorResponse(it, e.message ?: "Dados de solicitação inválidos") }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleGeneralException(e: Exception): ErrorResponse? {
        log.error("method=handleGeneralException |", e)
        return traceId()?.let { ErrorResponse(it, "Erro interno do servidor") }
    }

    private fun traceId(): String? {
        return Span.current().spanContext.traceId
    }

}
