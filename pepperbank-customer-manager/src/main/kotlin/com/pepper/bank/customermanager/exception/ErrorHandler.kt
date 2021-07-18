package com.pepper.bank.customermanager.exception

import com.pepper.bank.handler.handlers.ErrorHandler
import com.pepper.bank.handler.pojo.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ErrorHandler: ErrorHandler() {

    override fun customHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: Exception): ResponseEntity<ErrorMessage>? {
        return when (ex) {
            is CustomerValidationException -> customerValidationExceptionHandler(codeError, servletRequest, ex)
            else -> null
        }
    }

    private fun customerValidationExceptionHandler(
        codeError: String,
        servletRequest: HttpServletRequest,
        ex: CustomerValidationException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            codeError + (ex.message ?: "Fail in customer-manager"),
            HttpStatus.BAD_REQUEST
        )
    }
}