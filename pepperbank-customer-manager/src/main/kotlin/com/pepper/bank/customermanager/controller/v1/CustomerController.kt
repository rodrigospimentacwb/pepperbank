package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import com.pepper.bank.api.v1.CustomerApi
import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepperbank.utils.converters.JsonConverter
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CustomerController(
    private val customerService: CustomerService
): CustomerApi {

    override fun listAllClients(): List<CustomerTO> {
        return JsonConverter.convert(customerService.getAll(),CustomerTO::class.java)
    }
//
//    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
//    fun getCustomerById(@PathVariable id: String): ResponseEntity<CustomerTO> {
//        return ResponseEntity(JsonConverter.convert(customerService.getByID(id),CustomerTO::class.java), HttpStatus.OK)
//    }
//
//    @GetMapping("cpf/{cpf}")
//    fun getCustomerByCPF(@PathVariable cpf: String): ResponseEntity<CustomerTO> {
//        return ResponseEntity(JsonConverter.convert(customerService.getByCPF(cpf),CustomerTO::class.java), HttpStatus.OK)
//    }
//
//    @PostMapping
//    fun createCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<CustomerTO> {
//        return ResponseEntity(JsonConverter.convert(customerService.create(customer),CustomerTO::class.java), HttpStatus.OK)
//    }
//
//    @PutMapping
//    fun updateCustomer(@Valid @RequestBody customer: Customer): ResponseEntity<CustomerTO> {
//        return ResponseEntity(JsonConverter.convert(customerService.update(customer),CustomerTO::class.java), HttpStatus.OK)
//    }
//
//    @DeleteMapping("/{id}")
//    fun deleteCustomerById(@PathVariable id: String): ResponseEntity<String> {
//        customerService.deleteCustomerByUUID(id)
//        return ResponseEntity(HttpStatus.OK)
//    }
}