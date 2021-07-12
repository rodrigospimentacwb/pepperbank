package com.pepper.bank.handler.feign

import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.ForbbindenException
import com.pepper.bank.handler.exception.NotAuthorizedException
import com.pepper.bank.handler.exception.NotFoundException
import feign.Response
import feign.codec.ErrorDecoder

open class PepperErrorDecoder : ErrorDecoder {

    override fun decode(methodKey: String?, response: Response): Exception? {
        return when (response.status()) {
            400 -> BadRequestException("")
            401 -> NotAuthorizedException("")
            403 -> ForbbindenException("")
            404 -> NotFoundException("")
            405 -> BadRequestException("")
            else -> Exception("Generic error")
        }
    }
}