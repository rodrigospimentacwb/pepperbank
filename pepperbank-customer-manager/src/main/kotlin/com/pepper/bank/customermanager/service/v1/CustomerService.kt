package com.pepper.bank.customermanager.service.v1

import br.com.caelum.stella.validation.CPFValidator
import com.pepper.bank.customermanager.repository.CustomerRepository
import com.pepper.bank.handler.exception.BadRequestException
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.model.commons.Customer
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Optional
import java.util.UUID
import javax.transaction.Transactional
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE


@Service
class CustomerService(val customerRepository: CustomerRepository) {

    fun getAll(): List<Customer> {
        try {
            var customers:List<Customer> = customerRepository.findAll() as List<Customer>
            return if (customers.isNotEmpty()) customers else throw NotFoundException(MESSAGE.NO_CLIENT_FOUND)
        } catch (e: NotFoundException) {
            throw e
        } catch (e2: Exception) {
            throw RuntimeException()
        }
    }

    @Throws(CustomerValidationException::class)
    fun validateBirthDate(birthDate: LocalDate?) {
        when {
            birthDate == null -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_NULL)}
            birthDate.isAfter(LocalDate.now()) -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE) }
        }
    }

    @Throws(CustomerValidationException::class)
    fun validateName(name: String?) {
        when {
            name.isNullOrBlank() -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK)}
            name.length > 100 -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_NAME_LONG_100_CHARACTERS)}
        }
    }

    @Throws(CustomerValidationException::class, NotFoundException::class)
    fun validateChangeCPF(customer:Customer) {
        var customerFound:Optional<Customer> = customer.id?.let { customerRepository.findById(it) } ?: run { Optional.empty()}
        when {
            !customerFound.isPresent -> {
                throw NotFoundException("Customer id: ${customer.id} not found") }
            customerFound.get().cpf.isNullOrBlank() -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK) }
            customerFound.get().cpf != customer.cpf -> {
                throw CustomerValidationException(MESSAGE.CUSTOMER_CPF_CHANGED)}
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

    fun findByCPF(cpf:String): Optional<Customer> = customerRepository.findByCpf(cpf)

    fun findById(id:UUID): Optional<Customer> = customerRepository.findById(id)

    fun getByID(id: String): Customer {
        try {
            var customer = findById(UUID.fromString(id))
            return if (customer.isPresent) customer.get() else throw NotFoundException("Customer id: $id not found")
        } catch (e: NotFoundException) {
            throw e
        } catch (e1: IllegalArgumentException) {
            throw BadRequestException("Invalid format id: $id")
        } catch (e2: Exception) {
            throw RuntimeException()
        }
    }

    fun getByCPF(cpf: String): Customer {
        try {
            validateCPF(cpf)
            var customer = findByCPF(cpf)
            return if (customer.isPresent) customer.get() else throw NotFoundException("Customer cpf: $cpf not found")
        } catch (e: NotFoundException) {
            throw e
        } catch (e1: CustomerValidationException) {
            throw e1
        } catch (e2: Exception) {
            throw RuntimeException()
        }
    }

    @Transactional
    @Throws(CustomerValidationException::class, Exception::class)
    fun create(customer: Customer): Customer {
        ifExistsCPF(customer.cpf)
        customer.id?.let { throw CustomerValidationException(MESSAGE.CUSTOMER_ID_INVALID) }
        customer.id = generateNewCustomerUUID()
        validateBirthDate(customer.birthDate)
        validateName(customer.name)
        customer.phones?.forEach(){it.customer = customer}
        return customerRepository.save(customer);
    }

    @Transactional
    @Throws(CustomerValidationException::class, Exception::class)
    fun update(customer: Customer): Customer {
        if(customer.id == null){
            throw CustomerValidationException(MESSAGE.CUSTOMER_ID_INVALID)
        }
        validateChangeCPF(customer)
        validateBirthDate(customer.birthDate)
        validateName(customer.name)
        customer.phones?.forEach(){it.customer = customer}
        return customerRepository.save(customer);
    }

    fun deleteCustomerByUUID(uuid: String) {
        var uuidTransform:UUID? = null
        try {
            uuidTransform = UUID.fromString(uuid)
        }catch (ex: Exception){
            throw CustomerValidationException(MESSAGE.CUSTOMER_ID_INVALID)
        }
        deleteCustomerByUUID(UUID.fromString(uuid))
    }

    @Transactional
    @Throws(CustomerValidationException::class, NotFoundException::class)
    fun deleteCustomerByUUID(uuid: UUID) {
        try {
            var customer:Optional<Customer> = findById(uuid)
            if(customer.isPresent){
                customerRepository.delete(customer.get())
            }else{
                throw NotFoundException("Customer id: $uuid not found")
            }
        } catch (ex: NotFoundException){
            throw ex
        } catch (e: Exception){
            throw CustomerValidationException(MESSAGE.CUSTOMER_DELETE_ERROR)
        }
    }

    @Transactional
    @Throws(CustomerValidationException::class)
    fun delete(customer: Customer) {
        try {
            customerRepository.delete(customer)
        } catch (e: Exception){
            throw CustomerValidationException(MESSAGE.CUSTOMER_DELETE_ERROR)
        }
    }

    fun generateNewCustomerUUID():UUID{
        var contMaxAttempts:Int = 0
        var uuid: UUID? = null
        while(true){
            uuid = UUID.randomUUID();
            try {
                ifExistsId(uuid)
                return uuid
            } catch (ex: Exception){
                contMaxAttempts += 1
                if(contMaxAttempts >= 10){
                    throw ex
                }
            }
        }
    }
}