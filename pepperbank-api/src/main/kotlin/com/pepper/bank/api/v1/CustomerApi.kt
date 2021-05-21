package com.pepper.bank.api.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus

interface CustomerApi {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(
        value = ["/api/v1/customers"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun listAllClients(): List<CustomerTO>
}