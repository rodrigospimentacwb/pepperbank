package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.constants.CustomerServiceMessage
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.repository.commons.CustomerRepository
import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.NotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.UUID
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE

@Service
class CustomerService(val customerRepository: CustomerRepository) {

    @Throws(CustomerValidationException::class)
    fun validateBirthDate(birthDate: LocalDate?): Boolean {
        return when {
            birthDate == null ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_NULL)
            birthDate.isAfter(LocalDate.now()) ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE)
            else -> {
                return true
            }
        }
    }

    @Throws(CustomerValidationException::class)
    fun validateName(name: String?): Boolean {
        return when {
            name.isNullOrBlank() ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK)
            name.length > 100 ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_LONG_100_CHARACTERS)
            else -> {
                return true
            }
        }
    }

    fun create(customer: Customer): Customer {
        // Criar validações


        customer.id = null
        return this.customerRepository.save(customer);
    }

    fun getByID(id: String): Customer {
        try {
            return this.customerRepository.findById(UUID.fromString(id))
                .orElseThrow { NotFoundException("Customer id: $id not found") }
        } catch (e: NotFoundException) {
            throw e
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid format id: $id")
        } catch (e1: Exception) {
            throw RuntimeException()
        }
    }

    fun getByCPF(cpf: String): Customer? {
        TODO("Not yet implemented")
    }

    fun update(customer: Customer) {
        TODO("Not yet implemented")
    }

    fun delete(id: UUID) {
        TODO("Not yet implemented")
    }

    fun getAll(): List<Customer> {
        TODO("Not yet implemented")
    }



}