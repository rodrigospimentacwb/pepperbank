package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.customermanager.dto.customer.CustomerTO
import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.model.commons.Customer
import com.pepperbank.utils.converters.JsonConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping(value = ["/api/v1/customers"])
class CustomerController {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun listAllClients(): ResponseEntity<List<CustomerTO>> {
        return ResponseEntity(JsonConverter.convert(customerService.getAll(),CustomerTO::class.java), HttpStatus.OK)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCustomerById(@PathVariable id: String): ResponseEntity<CustomerTO> {
        return ResponseEntity(JsonConverter.convert(customerService.getByID(id),CustomerTO::class.java), HttpStatus.OK)
    }

    @GetMapping("cpf/{cpf}")
    fun getCustomerByCPF(@PathVariable cpf: String): ResponseEntity<CustomerTO> {
        return ResponseEntity(JsonConverter.convert(customerService.getByCPF(cpf),CustomerTO::class.java), HttpStatus.OK)
    }

    @PostMapping
    fun createCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<CustomerTO> {
        return ResponseEntity(JsonConverter.convert(customerService.create(customer),CustomerTO::class.java), HttpStatus.OK)
    }

    @PutMapping
    fun updateCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<CustomerTO> {
        return ResponseEntity(JsonConverter.convert(customerService.update(customer),CustomerTO::class.java), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(@PathVariable id: String): ResponseEntity<String> {
        customerService.deleteCustomerByUUID(id)
        return ResponseEntity(HttpStatus.OK)
    }
}