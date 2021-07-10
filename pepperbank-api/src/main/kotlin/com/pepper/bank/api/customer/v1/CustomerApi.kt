package com.pepper.bank.api.customer.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.UUID
import javax.validation.Valid

@FeignClient(name = "customerManager", url = "\${pepper.bank.customer.manager}")
interface CustomerApi {

    @ResponseStatus(OK)
    @GetMapping(
        value = ["/api/v1/customers"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun listAllClients(): List<CustomerTO>

    @ResponseStatus(OK)
    @GetMapping(
        value = ["/api/v1/customers/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getCustomerById(@PathVariable id: String): CustomerTO

    @ResponseStatus(OK)
    @GetMapping(
        value = ["/api/v1/customers/cpf/{cpf}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getCustomerByCPF(@PathVariable cpf: String): CustomerTO

    @ResponseStatus(CREATED)
    @PostMapping(
        value = ["/api/v1/customers"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createCustomer(@Valid @RequestBody customerTO: CustomerTO): CustomerTO

    @ResponseStatus(OK)
    @PutMapping(
        value = ["/api/v1/customers/{id}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateCustomer(@PathVariable id: UUID, @Valid @RequestBody customerTO: CustomerTO): CustomerTO

    @ResponseStatus(NO_CONTENT)
    @DeleteMapping("api/v1/customers/{id}")
    fun deleteCustomerById(@PathVariable id: String)
}