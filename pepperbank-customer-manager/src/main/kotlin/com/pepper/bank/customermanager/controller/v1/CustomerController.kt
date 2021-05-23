package com.pepper.bank.customermanager.controller.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import com.pepper.bank.api.v1.CustomerApi
import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.model.commons.Customer
import com.pepperbank.utils.converters.JsonConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class CustomerController(
    private val customerService: CustomerService
): CustomerApi {

    override fun listAllClients(): List<CustomerTO> {
        return JsonConverter.convert(customerService.getAll(),CustomerTO::class.java)
    }

    override fun getCustomerById(id: String): CustomerTO {
        return JsonConverter.convert(customerService.getByID(id),CustomerTO::class.java)
    }

    override fun getCustomerByCPF(cpf: String): CustomerTO {
        return JsonConverter.convert(customerService.getByCPF(cpf),CustomerTO::class.java)
    }

    override fun createCustomer(customerTO: CustomerTO): CustomerTO {
        val customer: Customer = customerService.toCustomer(customerTO)
        return JsonConverter.convert(customerService.create(customer),CustomerTO::class.java)
    }

    override fun updateCustomer(customerTO: CustomerTO): CustomerTO {
        val customer:Customer = customerService.toCustomer(customerTO)
        return JsonConverter.convert(customerService.update(customer),CustomerTO::class.java)
    }

    override fun deleteCustomerById(id: String) {
        customerService.deleteCustomerByUUID(id)
    }
}