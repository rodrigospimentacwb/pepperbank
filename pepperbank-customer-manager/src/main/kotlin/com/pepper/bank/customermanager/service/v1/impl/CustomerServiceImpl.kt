package com.pepper.bank.customermanager.service.v1.impl

import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.repository.commons.CustomerRepository
import com.pepper.bank.utils.exception.BadRequestException
import com.pepper.bank.utils.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.Validator
import javax.validation.ValidatorFactory

@Component
class CustomerServiceImpl: CustomerService {

    @Autowired
    lateinit var customerRepository: CustomerRepository

    override fun create(customer: Customer): Customer {
        try {

            val factory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
            val validator: Validator = factory.getValidator()
            val constraintViolations: Set<ConstraintViolation<Customer>> = validator.validate(customer)
            println("************ Array ConstraintValidations: " + constraintViolations)
            for (const in constraintViolations) {
                println("************" + const)
            }

            // Criar validações
            customer.id = null
            return this.customerRepository.save(customer);
        } catch (e1: Exception){
            throw RuntimeException()
        }
    }

    override fun getByID(id: String): Customer {
        try {
            return this.customerRepository.findById(UUID.fromString(id)).orElseThrow{ NotFoundException("Customer id: $id not found") }
        } catch (e:NotFoundException){
            throw e
        } catch (e:IllegalArgumentException){
            throw BadRequestException("Invalid format id: $id")
        } catch (e1: Exception){
            throw RuntimeException()
        }
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