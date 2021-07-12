package com.pepper.bank.accountmanager.exception

import com.pepper.bank.handler.exception.AccountValidationException
import com.pepper.bank.handler.handlers.ErrorHandler
import com.pepper.bank.handler.pojo.ErrorMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ErrorHandler: ErrorHandler() {

    override fun customHandler(
        servletRequest: HttpServletRequest,
        ex: Exception): ResponseEntity<ErrorMessage>? {
        return when (ex) {
            is AccountValidationException -> accountValidationExceptionHandler(servletRequest, ex)
            else -> null
        }
    }

    private fun accountValidationExceptionHandler(
        servletRequest: HttpServletRequest,
        ex: AccountValidationException
    ): ResponseEntity<ErrorMessage> {
        return buildReponse(
            servletRequest,
            ex.javaClass.simpleName,
            ex.message ?: "Fail in account-manager",
            HttpStatus.BAD_REQUEST
        )
    }
}