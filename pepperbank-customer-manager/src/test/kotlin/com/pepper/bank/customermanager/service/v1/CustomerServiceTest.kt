package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.configuration.TestsConfig
import com.pepper.bank.customermanager.repository.CustomerRepository
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.model.commons.Customer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.*
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE


@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestsConfig::class])
class CustomerServiceTest : DefaultTestValues() {

    @MockBean
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var customerService: CustomerService

    @Rule
    @JvmField
    var expectedEx: ExpectedException = ExpectedException.none()

    @Test
    fun `should validate the date of birth`() {
        var customer = generatedTestCustomerDefault()
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
        var customer = generatedTestCustomerDefault()
        customerService.validateName(customer.name)
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
        customerService.validateCPF(CPF_VALID)
    }

    @Test
    fun `should throw CustomerValidationException if cpf already exists`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE)
        }
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findByCpf(customer.cpf)).thenReturn(Optional.of(customer))
        customerService.ifExistsCPF(customer.cpf)
    }

    @Test
    fun `should not throw CustomerValidationException if cpf not exists`(){
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findByCpf(customer.cpf)).thenReturn(Optional.empty())
        customerService.ifExistsCPF(customer.cpf)
    }

    @Test
    fun `should throw CustomerValidationException if id in use`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_ID_INVALID)
        }
        val id:UUID = UUID.randomUUID()
        var customer = generatedTestCustomerDefault()
        customer.id = id
        Mockito.`when`(customerRepository.findById(id)).thenReturn(Optional.of(customer))
        customerService.ifExistsId(id)
    }

    @Test
    fun `should save client`() {
        var customer = generatedTestCustomerDefault()
        Mockito
            .`when`(customerRepository.save(Mockito.any(Customer::class.java)))
            .thenReturn(customer)
        var customerSaved = customerService.create(customer)
        Assert.assertNotNull(customerSaved)
        Assert.assertTrue(ReflectionEquals(customerSaved, "id").matches(customer))
        Mockito.verify(customerRepository,Mockito.times(1)).save(customer)
    }

    @Test
    fun `should validate if the cpf has been changed`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_CHANGED)
        }
        var customerMock = generatedGalileuCustomer()
        Mockito.`when`(customerMock.id?.let { customerRepository.findById(it) }).thenReturn(Optional.of(customerMock))
        var customer = generatedGalileuCustomer()
        customer.cpf = "01234567890"
        customerService.validateChangeCPF(customer)
    }

    @Test
    fun `should generate new UUID to customer`(){
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.empty())
        var uuid: UUID = customerService.generateNewCustomerUUID()
        println("UUID generated: $uuid")
    }

    @Test
    fun `should throw an exception if the maximum number of attempts to generate the new UUID exceeds 10`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_ID_INVALID)
        }
        var customerMock = generatedGalileuCustomer()
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.of(customerMock))
        customerService.generateNewCustomerUUID()
    }

    @Test
    fun `should get all customers`(){
        Mockito.`when`(customerRepository.findAll()).thenReturn(generateListCustomers())
        var customers:List<Customer> = customerService.getAll()
        Assert.assertEquals(3,customers.size)
    }

    @Test
    fun `should throw exception notFound if get all customers is empty`(){
        with(expectedEx) {
            expect(NotFoundException::class.java)
        }
        Mockito.`when`(customerRepository.findAll()).thenReturn(emptyList())
        customerService.getAll()
    }

    @Test
    fun `should throw exception if UUID invalid in deleteCustomerByUUID`(){
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_ID_INVALID)
        }
        customerService.deleteCustomerByUUID(INVALID_UUID)
    }

    @Test
    fun `should throw exception if UUID not exists in deleteCustomerByUUID`(){
        var uuid = UUID.randomUUID()
        with(expectedEx) {
            expect(NotFoundException::class.java)
            expectMessage("Customer id: $uuid not found")
        }
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.empty())
        customerService.deleteCustomerByUUID(uuid)
    }

    @Test
    fun `should deleteCustomerByUUID for String UUID`(){
        var uuid = UUID.randomUUID()
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.of(customer))
        customerService.deleteCustomerByUUID(uuid.toString())
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }

    @Test
    fun `should deleteCustomerByUUID for UUID`(){
        var uuid = UUID.randomUUID()
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.of(customer))
        customerService.deleteCustomerByUUID(uuid)
        Mockito.verify(customerRepository, Mockito.times(1)).delete(customer);
    }

    @Test
    fun `should convert customerTO to customer`(){
        var customer = generatedGalileuCustomer()
        var parsedCustomer = customerService.toCustomer(generatedGalileuCustomerTO())
        Assert.assertEquals(customer.id,parsedCustomer.id)
        Assert.assertEquals(customer.name,parsedCustomer.name)
        Assert.assertEquals(customer.cpf,parsedCustomer.cpf)
        Assert.assertEquals(customer.birthDate,parsedCustomer.birthDate)
        Assert.assertEquals(customer.email,parsedCustomer.email)
        Assert.assertEquals(customer.phones!!.size,parsedCustomer.phones!!.size)
        Assert.assertEquals(customer.phones!![0].ddd, parsedCustomer.phones!![0].ddd)
        Assert.assertEquals(customer.phones!![0].phone, parsedCustomer.phones!![0].phone)
        Assert.assertEquals(customer.phones!![1].ddd, parsedCustomer.phones!![1].ddd)
        Assert.assertEquals(customer.phones!![1].phone, parsedCustomer.phones!![1].phone)
    }
}