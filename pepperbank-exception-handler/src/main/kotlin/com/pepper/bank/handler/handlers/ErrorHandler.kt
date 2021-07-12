package com.pepper.bank.handler.handlers

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.CustomFeignException
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.FormatDateTimeException
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.handler.pojo.ErrorMessage
import feign.RetryableException
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import java.lang.reflect.UndeclaredThrowableException
import java.time.LocalDateTime
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class ErrorHandler {

    private val logger = LogManager.getLogger(this::class.java)

    companion object {

        fun buildReponse(
            servletRequest: HttpServletRequest,
            error: String,
            message: String,
            status: HttpStatus
        ): ResponseEntity<ErrorMessage> {
            val httpHeaders = HttpHeaders()
            httpHeaders.add("Content-Type", "application/json; charset=utf-8")
            return ResponseEntity(
                ErrorMessage(
                    LocalDateTime.now(),
                    status.value(),
                    error,
                    message,
                    servletRequest.servletPath,
                    servletRequest.method
                ), httpHeaders, status
            )
        }
    }

    @ExceptionHandler(Exception::class)
    fun geralExceptionHandler(
        request: HttpServletRequest,
        response: HttpServletResponse,
        ex: Exception
    ): ResponseEntity<ErrorMessage> {
        val codeError = "Error code: " + UUID.randomUUID() + " ";
        logger.error("$codeError Exception: [${ex.message}].", ex)
        return customHandler(codeError, request, ex)
            ?: when (ex) {
                is JsonParseException -> return jsonParseExceptionHandler(codeError, request, ex)
                is HttpServerErrorException -> return internalServerErrorExceptionHandler(codeError, request, ex)
                is NotFoundException -> return notFoundExceptionHandler(codeError, request, ex)
                is BadRequestException -> return badRequestExceptionHandler(codeError, request, ex)
                is NullPointerException -> return nullPointerExceptionHandler(codeError, request, ex)
                is IllegalArgumentException -> return illegalArgumentExceptionHandler(codeError, request, ex)
                is MethodArgumentNotValidException -> return methodArgumentNotValidExceptionHandler(codeError, request, ex)
                is FormatDateTimeException -> return formatDateTimeExceptionHandler(codeError, request, ex)
                is UndeclaredThrowableException -> return undeclaredThrowableExceptionHandler(codeError, request, ex)
                is RetryableException -> return retryableExceptionHandler(codeError, request, ex)
                is CustomFeignException -> return feignExceptionHandler(codeError, request, ex)
                is MismatchedInputException -> return mismatchedInputExceptionHandler(codeError, request, ex)
                is HttpRequestMethodNotSupportedException -> return httpRequestMethodNotSupportedExceptionHandler(codeError, request, ex)
                is HttpMediaTypeNotSupportedException -> return httpMediaTypeNotSupportedExceptionHandler(codeError, request, ex)
                is RuntimeException -> return runtimeExceptionHandler(codeError, request, ex)

                else -> buildReponse(
                    request,
                    "Unknown Error",
                    "Please validate the application logs",
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
    }

    public open fun customHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: Exception
    ): ResponseEntity<ErrorMessage>? {
        return null
    }

    private fun undeclaredThrowableExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: UndeclaredThrowableException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            "$codeError UndeclaredThrowableException verify method call to add annotation @Throws(Exception.class)",
            HttpStatus.BAD_REQUEST
        )
    }

    private fun retryableExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: RetryableException
    ): ResponseEntity<ErrorMessage> {
        var cause = if (ex.cause != null) ex.cause!!.message else "unidentified cause"
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            "$codeError Feign request error: $cause'",
            HttpStatus.BAD_REQUEST
        )
    }

    private fun feignExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: CustomFeignException
    ): ResponseEntity<ErrorMessage> {
        val cause: String = if (ex.cause?.cause != null) ex.cause.cause!!.message.toString() else "unidentified cause"
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            "$codeError Feign request error: $cause' ${ex.message}",
            HttpStatus.BAD_REQUEST
        )
    }

    fun jsonParseExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: JsonParseException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Invalid Json"),
            HttpStatus.BAD_REQUEST
        )
    }

    fun internalServerErrorExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: HttpServerErrorException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Internal error server"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun runtimeExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: RuntimeException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Internal error server"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun httpRequestMethodNotSupportedExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: HttpRequestMethodNotSupportedException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Request method '" + servletRequest.method + "' not supported"),
            HttpStatus.BAD_REQUEST
        )
    }

    fun httpMediaTypeNotSupportedExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: HttpMediaTypeNotSupportedException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Media type '" + servletRequest.method + "' not supported"),
            HttpStatus.BAD_REQUEST
        )
    }

    fun notFoundExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: NotFoundException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + ex.message!!,
            HttpStatus.NOT_FOUND
        )
    }

    fun badRequestExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: BadRequestException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Invalid send data"),
            HttpStatus.BAD_REQUEST
        )
    }

    fun nullPointerExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: NullPointerException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Null pointer error"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun mismatchedInputExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: MismatchedInputException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Jackson error"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }


    fun illegalArgumentExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: IllegalArgumentException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Argument invalid"),
            HttpStatus.BAD_REQUEST
        )
    }

    fun methodArgumentNotValidExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorMessage> {
        val errors: MutableList<String> = ArrayList()
        ex.getBindingResult().getFieldErrors().forEach { errors.add(it.field + ": " + it.defaultMessage) }
        ex.getBindingResult().getGlobalErrors().forEach { errors.add(it.objectName + ": " + it.defaultMessage) }
        logger.error("Fields: [${errors.toString()}].")
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + errors.toString(),
            HttpStatus.BAD_REQUEST
        )
    }

    fun formatDateTimeExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: FormatDateTimeException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Fail to convert String to LocalDate"),
            HttpStatus.BAD_REQUEST
        )
    }
}