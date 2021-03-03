package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.config.TestConfig
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.model.commons.Phone
import com.pepper.bank.repository.commons.CustomerRepository
import com.pepperbank.utils.converters.DateTimeConverter
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.lang.Exception
import java.time.LocalDate
import java.util.Optional
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE


@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestConfig::class])
class CustomerServiceTest : DefaultTestValues() {

    @MockBean
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var customerService: CustomerService

    @Rule
    @JvmField
    var expectedEx: ExpectedException = ExpectedException.none()

    private fun generatedTestCustomer(): Customer = Customer(cpf = CPF_VALID,
        email = EMAIL,
        name = NAME,
        birthDate = DateTimeConverter.convertStringToLocalDate(BIRTHDATE),
        phones = arrayListOf<Phone>(
            Phone(PHONE_1_DDD, PHONE_1_NUMBER),
            Phone(PHONE_2_DDD, PHONE_2_NUMBER)
        ))

    @Test
    fun `should validate the date of birth`() {
        var customer = generatedTestCustomer()
        customerService.validateBirthDate(customer.birthDate)
    }

    @Test
    fun `should throw CustomerValidationException if birth date is null`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_NULL)
        }
        customerService.validateBirthDate(null)
    }

    @Test
    fun `should throw CustomerValidationException if the date of birth is greater than the current date`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE)
        }
        customerService.validateBirthDate(LocalDate.now().plusDays(1))
    }

    @Test
    fun `should validate customer name`() {
        var customer = generatedTestCustomer()
        customerService.validateName(customer.name) //AQUIII
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name is null`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK)
        }
        customerService.validateName(null)
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name is blank`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK)
        }
        customerService.validateName("")
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name be longer than 100 characters`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_NAME_LONG_100_CHARACTERS)
        }
        customerService.validateName(NAME_INVALID_MORE_100_CHACARACTERS)
    }

    @Test
    fun `should throw CustomerValidationException if CPF is null`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK)
        }
        customerService.validateCPF(null)
    }

    @Test
    fun `should throw CustomerValidationException if CPF is blank`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK)
        }
        customerService.validateCPF("")
    }

    @Test
    fun `should throw CustomerValidationException if CPF is invalid`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_INVALID)
        }
        customerService.validateCPF(CPF_INVALID)
    }

    @Test
    fun `should not throw CustomerValidationException if CPF is valid`(){
        customerService.validateCPF(CPF_VALID) // AQUIII
    }

    @Test
    fun `should throw CustomerValidationException if cpf already exists`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE)
        }
        var customer = generatedTestCustomer()
        Mockito.`when`(customerRepository.findByCPF(customer.cpf)).thenReturn(Optional.of(customer))
        customerService.ifExistsCPF(customer.cpf)
    }

    @Test
    fun `should not throw CustomerValidationException if cpf not exists`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE)
        }
        var customer = generatedTestCustomer()
        Mockito.`when`(customerRepository.findByCPF(customer.cpf)).thenReturn(Optional.empty())
        customerService.ifExistsCPF(customer.cpf)
    }
}