package com.pepper.bank.handler.handlers

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.FormatDateTimeException
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.handler.pojo.ErrorMessage
import org.apache.logging.log4j.LogManager
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import java.lang.reflect.UndeclaredThrowableException
import java.time.LocalDateTime
import java.util.ArrayList
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import java.nio.charset.StandardCharsets


@ControllerAdvice
class ErrorHandler {

    private val LOG = LogManager.getLogger(this.javaClass)

    companion object {

        fun buildReponse(
            servletRequest: HttpServletRequest,
            error: String,
            message: String,
            status: HttpStatus
        ): ResponseEntity<ErrorMessage> {
            val httpHeaders = HttpHeaders()
            httpHeaders.add("Content-Type","application/json; charset=utf-8")
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
        LOG.error("Exception error: [${ex.message}].", ex)
        return when (ex) {
            is JsonParseException -> return jsonParseExceptionHandler(request, response, ex)
            is HttpServerErrorException -> return internalServerErrorExceptionHandler(request, response, ex)
            is NotFoundException -> return notFoundExceptionHandler(request, response, ex)
            is BadRequestException -> return badRequestExceptionHandler(request, response, ex)
            is NullPointerException -> return nullPointerExceptionHandler(request, response, ex)
            is IllegalArgumentException -> return illegalArgumentExceptionHandler(request, response, ex)
            is MethodArgumentNotValidException -> return methodArgumentNotValidExceptionHandler(request, response, ex)
            is FormatDateTimeException -> return formatDateTimeExceptionHandler(request, response, ex)
            is CustomerValidationException -> return customerValidationExceptionHandler(request, response, ex)
            is UndeclaredThrowableException -> return undeclaredThrowableExceptionHandler(request, response, ex)
            is RuntimeException -> return runtimeExceptionHandler(request, response, ex)
            is MismatchedInputException -> return mismatchedInputExceptionHandler(request, response, ex)
            is HttpRequestMethodNotSupportedException -> return httpRequestMethodNotSupportedExceptionHandler(
                request,
                response,
                ex
            )
            else -> buildReponse(
                request,
                "Unknown Error",
                "Please validate the application logs",
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    private fun undeclaredThrowableExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: UndeclaredThrowableException): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            "UndeclaredThrowableException verify method call to add annotation @Throws(Exception.class)",
            HttpStatus.BAD_REQUEST
        )
    }

    private fun customerValidationExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: CustomerValidationException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Fail to convert customer data",
            HttpStatus.BAD_REQUEST
        )
    }

    fun jsonParseExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: JsonParseException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Invalid Json",
            HttpStatus.BAD_REQUEST
        )
    }

    fun internalServerErrorExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: HttpServerErrorException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Internal error server",
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun runtimeExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: RuntimeException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Internal error server",
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun httpRequestMethodNotSupportedExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: HttpRequestMethodNotSupportedException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Request method '" + servletRequest.method + "' not supported",
            HttpStatus.BAD_REQUEST
        )
    }

    fun notFoundExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: NotFoundException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message!!,
            HttpStatus.NOT_FOUND
        )
    }

    fun badRequestExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: BadRequestException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Invalid send data",
            HttpStatus.BAD_REQUEST
        )
    }

    fun nullPointerExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: NullPointerException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Null pointer error",
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }

    fun mismatchedInputExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: MismatchedInputException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Jackson error",
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }


    fun illegalArgumentExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: IllegalArgumentException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Argument invalid",
            HttpStatus.BAD_REQUEST
        )
    }

    fun methodArgumentNotValidExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: MethodArgumentNotValidException
    ): ResponseEntity<ErrorMessage> {
        val errors: MutableList<String> = ArrayList()
        ex.getBindingResult().getFieldErrors().forEach { errors.add(it.field + ": " + it.defaultMessage) }
        ex.getBindingResult().getGlobalErrors().forEach { errors.add(it.objectName + ": " + it.defaultMessage) }
        LOG.error("Fields: [${errors.toString()}].")
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            errors.toString(),
            HttpStatus.BAD_REQUEST
        )
    }

    fun formatDateTimeExceptionHandler(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        ex: FormatDateTimeException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Fail to convert String to LocalDate",
            HttpStatus.BAD_REQUEST
        )
    }
}