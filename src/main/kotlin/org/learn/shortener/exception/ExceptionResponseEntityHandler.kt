package org.learn.shortener.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@RestControllerAdvice
class ExceptionResponseEntityHandler : ResponseEntityExceptionHandler() {

    val _logger = LoggerFactory
        .getLogger(ExceptionResponseEntityHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun handleDefaultException(exception: Exception): ResponseEntity<ApiError?>? {
        _logger.error("Unhandled exception ", exception)
        val apiError = ApiError(
            exception.message)
        return ResponseEntity<ApiError?>(apiError, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(DataValidationException::class)
    fun dataValidationException(exception: DataValidationException): ResponseEntity<ApiError?>? {
        _logger.error("DataValidationException ", exception)
        val apiError = ApiError(exception.errorMessage)
        return ResponseEntity<ApiError?>(apiError, HttpStatus.BAD_REQUEST)
    }
}