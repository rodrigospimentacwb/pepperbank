package com.pepper.bank.utils.handlers

import com.fasterxml.jackson.core.JsonParseException
import com.pepper.bank.utils.exception.BadRequestException
import com.pepper.bank.utils.exception.NotFoundException
import com.pepper.bank.utils.model.ErrorMessage
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.Exception
import java.lang.NullPointerException
import java.time.LocalDateTime

@ControllerAdvice
class ErrorHandler {

    private val logger: Logger = LogManager.getLogger(ErrorHandler::class.java)

    @ExceptionHandler(JsonParseException::class)
    fun jsonParseExceptionHandler(servletRequest:HttpServletRequest,
                                  servletReponse: HttpServletResponse,
                                  exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Json Error",
            exception.message ?: "invalid json",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError::class)
    fun internalServerErrorExceptionHandler(servletRequest:HttpServletRequest,
                                            servletReponse: HttpServletResponse,
                                            exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Fatal Error",
            exception.message ?: "Internal error server",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(RuntimeException::class)
    fun runtimeExceptionHandler(servletRequest:HttpServletRequest,
                                servletReponse: HttpServletResponse,
                                exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Fatal Error",
            exception.message ?: "Internal error server",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun httpRequestMethodNotSupportedExceptionHandler(servletRequest:HttpServletRequest,
                                servletReponse: HttpServletResponse,
                                exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad request",
            exception.message ?: "Request method '" + servletRequest.method +"' not supported",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(servletRequest: HttpServletRequest,
                                 servletResponse: HttpServletResponse,
                                 exception: Exception):
            ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Not found",
            exception.message !!,
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun badRequestExceptionHandler(servletRequest: HttpServletRequest,
                                   servletResponse: HttpServletResponse,
                                   exception: Exception):
            ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            exception.message ?: "Invalid send data",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerExceptionExceptionHandler(servletRequest:HttpServletRequest,
                                             servletReponse: HttpServletResponse,
                                             exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Null Pointer",
            exception.message ?: "Null pointer error",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun IllegalArgumentExceptionHandler(servletRequest:HttpServletRequest,
                                        servletReponse: HttpServletResponse,
                                        exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage(LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Bad Request",
            exception.message ?: "Argument invalid",
            servletRequest.servletPath,
            servletRequest.method), HttpStatus.BAD_REQUEST)
    }
}