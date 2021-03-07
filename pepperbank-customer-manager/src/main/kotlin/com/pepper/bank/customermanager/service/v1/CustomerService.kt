package com.pepper.bank.customermanager.service.v1

import br.com.caelum.stella.validation.CPFValidator
import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.repository.commons.CustomerRepository
import org.apache.logging.log4j.LogManager
import org.hibernate.validator.constraints.br.CPF
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Optional
import java.util.UUID
import javax.validation.Validation
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE
import javax.validation.ValidatorFactory


@Service
class CustomerService(val customerRepository: CustomerRepository) {

    @Throws(CustomerValidationException::class)
    fun validateBirthDate(birthDate: LocalDate?) {
        when {
            birthDate == null ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_NULL)
            birthDate.isAfter(LocalDate.now()) ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE)
        }
    }

    @Throws(CustomerValidationException::class)
    fun validateName(name: String?) {
        when {
            name.isNullOrBlank() ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK)
            name.length > 100 ->
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_LONG_100_CHARACTERS)
        }
    }

    @Throws(CustomerValidationException::class)
    fun validateCPF(cpf:String?) {
        try {
            if(cpf.isNullOrBlank()){
                throw CustomerValidationException(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK)
            }else{
                var cpfValidator = CPFValidator()
                cpfValidator.assertValid(cpf)
            }
        }catch (e: CustomerValidationException){
            throw e
        }catch (e2: Exception){
            throw CustomerValidationException(MESSAGE.CUSTOMER_CPF_INVALID)
        }
    }

    @Throws(CustomerValidationException::class)
    fun ifExistsCPF(cpf:String){
        validateCPF(cpf)
        if(findByCPF(cpf).isPresent){
            throw CustomerValidationException(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE)
        }
    }

    @Throws(CustomerValidationException::class)
    fun ifExistsId(id:UUID){
        id?.let {
            if(findById(it).isPresent){
                throw CustomerValidationException(MESSAGE.CUSTOMER_ID_INVALID)
            }
        }
    }

    fun create(customer: Customer): Customer {
        ifExistsCPF(customer.cpf)
        customer.id?.let { ifExistsId(it) }
        validateBirthDate(customer.birthDate)
        validateName(customer.name)
        return customerRepository.save(customer);
    }

    fun findByCPF(cpf:String): Optional<Customer> = customerRepository.findByCpf(cpf)

    fun findById(id:UUID): Optional<Customer> = customerRepository.findById(id)

    fun getByID(id: String): Customer {
        try {
            var customer = findById(UUID.fromString(id))
            return if (customer.isPresent) customer.get() else throw NotFoundException("Customer id: $id not found")
        } catch (e: NotFoundException) {
            throw e
        } catch (e: IllegalArgumentException) {
            throw BadRequestException("Invalid format id: $id")
        } catch (e: Exception) {
            throw RuntimeException()
        }
    }
}