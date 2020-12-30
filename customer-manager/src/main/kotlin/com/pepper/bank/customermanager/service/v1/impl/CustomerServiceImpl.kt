package com.pepper.bank.customermanager.service.v1.impl

import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.domain.commons.Customer
import com.pepper.bank.repository.commons.CustomerRepository
import com.pepper.bank.utils.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class CustomerServiceImpl: CustomerService {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun create(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun getByID(id: UUID): Customer {
        return this.customerRepository.findById(id).orElseThrow{ NotFoundException("Customer id: $id not found") }
    }

    override fun getByCPF(cpf: String): Customer? {
        TODO("Not yet implemented")
    }

    override fun update(customer: Customer) {
        TODO("Not yet implemented")
    }

    override fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Customer> {
        TODO("Not yet implemented")
    }

}