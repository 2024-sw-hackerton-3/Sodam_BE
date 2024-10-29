package org.example.sodam.global.error

import org.example.sodam.global.error.exception.SodamException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource
) {

    @ExceptionHandler(SodamException::class)
    fun handlingPickException(e: SodamException): ResponseEntity<ErrorResponse> {
        val code = e.errorCode
        return ResponseEntity(
            ErrorResponse(code.status, code.message),
            HttpStatus.valueOf(code.status)
        )
    }
}
