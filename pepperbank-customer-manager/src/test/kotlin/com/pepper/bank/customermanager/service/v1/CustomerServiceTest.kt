package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.configuration.TestsConfig
import com.pepper.bank.customermanager.exception.CustomerValidationException
import com.pepper.bank.customermanager.repository.CustomerRepository
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.model.commons.Customer
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.Optional
import java.util.UUID
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE


@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestsConfig::class])
class CustomerServiceTest : DefaultTestValues() {

    @MockBean
    lateinit var customerRepository: CustomerRepository

    @Autowired
    lateinit var customerService: CustomerService

    @Test
    fun `should validate the date of birth`() {
        var customer = generatedTestCustomerDefault()
        customerService.validateBirthDate(customer.birthDate)
    }

    @Test
    fun `should throw CustomerValidationException if birth date is null`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateBirthDate(null) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_NULL))
    }

    @Test
    fun `should throw CustomerValidationException if the date of birth is greater than the current date`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateBirthDate(LocalDate.now().plusDays(1)) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE))
    }

    @Test
    fun `should validate customer name`() {
        var customer = generatedTestCustomerDefault()
        customerService.validateName(customer.name)
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name is null`() {
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateName(null) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK))
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name is blank`() {
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateName("") }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK))
    }

    @Test
    fun `should throw CustomerValidationException validate customer if name be longer than 100 characters`() {
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateName(NAME_INVALID_MORE_100_CHACARACTERS) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_NAME_LONG_100_CHARACTERS))
    }

    @Test
    fun `should throw CustomerValidationException if CPF is null`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateCPF(null) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK))
    }

    @Test
    fun `should throw CustomerValidationException if CPF is blank`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateCPF("") }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK))
    }

    @Test
    fun `should throw CustomerValidationException if CPF is invalid`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateCPF(CPF_INVALID) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_CPF_INVALID))
    }

    @Test
    fun `should not throw CustomerValidationException if CPF is valid`(){
        customerService.validateCPF(CPF_VALID)
    }

    @Test
    fun `should throw CustomerValidationException if cpf already exists`(){
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findByCpf(customer.cpf)).thenReturn(Optional.of(customer))

        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.ifExistsCPF(customer.cpf) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE))
    }

    @Test
    fun `should not throw CustomerValidationException if cpf not exists`(){
        var customer = generatedTestCustomerDefault()
        Mockito.`when`(customerRepository.findByCpf(customer.cpf)).thenReturn(Optional.empty())
        customerService.ifExistsCPF(customer.cpf)
    }

    @Test
    fun `should throw CustomerValidationException if id in use`(){
        val id:UUID = UUID.randomUUID()
        var customer = generatedTestCustomerDefault()
        customer.id = id
        Mockito.`when`(customerRepository.findById(id)).thenReturn(Optional.of(customer))

        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.ifExistsId(id) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_ID_INVALID))
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
        var customerMock = generatedGalileuCustomer()
        Mockito.`when`(customerMock.id?.let { customerRepository.findById(it) }).thenReturn(Optional.of(customerMock))
        var customer = generatedGalileuCustomer()
        customer.cpf = "01234567890"

        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.validateChangeCPF(customer) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_CPF_CHANGED))
    }

    @Test
    fun `should generate new UUID to customer`(){
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.empty())
        var uuid: UUID = customerService.generateNewCustomerUUID()
        println("UUID generated: $uuid")
    }

    @Test
    fun `should throw an exception if the maximum number of attempts to generate the new UUID exceeds 10`(){
        var customerMock = generatedGalileuCustomer()
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.of(customerMock))

        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.generateNewCustomerUUID() }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_ID_INVALID))
    }

    @Test
    fun `should get all customers`(){
        Mockito.`when`(customerRepository.findAll()).thenReturn(generateListCustomers())
        var customers:List<Customer> = customerService.getAll()
        Assert.assertEquals(3,customers.size)
    }

    @Test
    fun `should throw exception notFound if get all customers is empty`(){
        Mockito.`when`(customerRepository.findAll()).thenReturn(emptyList())

        Assert.assertThrows(
            NotFoundException::class.java
        ) { customerService.getAll() }
    }

    @Test
    fun `should throw exception if UUID invalid in deleteCustomerByUUID`(){
        val ex:CustomerValidationException = Assert.assertThrows(
            CustomerValidationException::class.java
        ) { customerService.deleteCustomerByUUID(INVALID_UUID) }
        Assert.assertTrue(ex.message.equals(MESSAGE.CUSTOMER_ID_INVALID))
    }

    @Test
    fun `should throw exception if UUID not exists in deleteCustomerByUUID`(){
        var uuid = UUID.randomUUID()
        Mockito.`when`(customerRepository.findById(Mockito.any(UUID::class.java))).thenReturn(Optional.empty())

        val ex:NotFoundException = Assert.assertThrows(
            NotFoundException::class.java
        ) { customerService.deleteCustomerByUUID(uuid) }
        Assert.assertTrue(ex.message.equals("Customer id: $uuid not found"))
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