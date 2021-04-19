package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.model.commons.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping(value = ["/api/v1/customers"])
class CustomerController {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: String): ResponseEntity<Customer> {
        return ResponseEntity(customerService.getByID(id), HttpStatus.OK)
    }

    @GetMapping("cpf/{cpf}")
    fun getCustomerByCPF(@PathVariable cpf: String): ResponseEntity<Customer> {
        return ResponseEntity(customerService.getByCPF(cpf), HttpStatus.OK)
    }

    @PostMapping
    fun createCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<Customer> {
        return ResponseEntity(customerService.create(customer), HttpStatus.OK)
    }

    @PutMapping
    fun updateCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<Customer> {
        return ResponseEntity(customerService.update(customer), HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteCustomerById(@PathVariable id: String): ResponseEntity<String> {
        customerService.deleteCustomerByUUID(id)
        return ResponseEntity(HttpStatus.OK)
    }
}