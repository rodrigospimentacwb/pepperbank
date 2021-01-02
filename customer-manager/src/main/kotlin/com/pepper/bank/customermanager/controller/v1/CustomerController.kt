package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.domain.commons.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID
import javax.validation.Valid
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory


@RestController
@RequestMapping(value = ["/api/v1/customers"])
class CustomerController {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping("/{id}")
    fun getCustomerId(@PathVariable id:String): ResponseEntity<Customer> {
        return ResponseEntity(customerService.getByID(id),HttpStatus.OK)
    }

    @PostMapping
    fun getCustomerId(@Valid @RequestBody customer: Customer): ResponseEntity<Customer> {
        return ResponseEntity(customerService.create(customer),HttpStatus.OK)
    }

}