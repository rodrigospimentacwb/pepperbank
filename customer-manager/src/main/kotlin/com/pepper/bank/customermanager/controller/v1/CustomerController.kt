package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.domain.commons.Customer
import com.pepper.bank.utils.exception.NotFoundException
import com.pepper.bank.utils.model.ErrorMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*
import java.util.UUID

@RestController
@RequestMapping(value = ["/api/v1/customers"])
class CustomerController {

    @Autowired
    lateinit var customerService: CustomerService

    @GetMapping("/{id}")
    fun getCustomerId(@PathVariable id:String): ResponseEntity<Customer> {
        //Sanitizar entrada
        return ResponseEntity(customerService.getByID(UUID.fromString(id)),HttpStatus.OK)
    }
}