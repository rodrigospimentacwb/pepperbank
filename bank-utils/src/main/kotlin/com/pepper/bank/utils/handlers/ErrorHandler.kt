package com.pepper.bank.utils.handlers

import com.fasterxml.jackson.core.JsonParseException
import com.pepper.bank.utils.exception.NotFoundException
import com.pepper.bank.utils.model.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpServerErrorException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.lang.Exception
import java.lang.NullPointerException

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler(JsonParseException::class)
    fun jsonParseExceptionHandler(servletRequest:HttpServletRequest, servletReponse: HttpServletResponse, exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage("Json Error", exception.message ?: "invalid json"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError::class)
    fun internalServerErrorExceptionHandler(servletRequest:HttpServletRequest, servletReponse: HttpServletResponse, exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage("Fatal Error", exception.message ?: "Internal error server"), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundExceptionHandler(servletRequest: HttpServletRequest, servletResponse: HttpServletResponse, exception: Exception):
            ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage("Not found", exception.message !!), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(NullPointerException::class)
    fun nullPointerExceptionExceptionHandler(servletRequest:HttpServletRequest, servletReponse: HttpServletResponse, exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage("Null Pointer", exception.message ?: "Null pointer error"), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun IllegalArgumentExceptionHandler(servletRequest:HttpServletRequest, servletReponse: HttpServletResponse, exception: Exception):ResponseEntity<ErrorMessage>{
        exception.printStackTrace()
        return ResponseEntity(ErrorMessage("Bad Request", exception.message ?: "Argument invalid"), HttpStatus.BAD_REQUEST)
    }
}