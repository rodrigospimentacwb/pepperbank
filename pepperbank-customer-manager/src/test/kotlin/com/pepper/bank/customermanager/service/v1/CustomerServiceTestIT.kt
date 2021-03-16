package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.config.TestConfig
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.model.commons.Customer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import java.util.Optional
import com.pepper.bank.customermanager.constants.CustomerServiceMessage.Companion as MESSAGE

@RunWith(value = SpringRunner::class)
@DataJpaTest
@TestPropertySource("/application-test.properties")
@SqlGroup(
        Sql("/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql("/clean-database.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD))
@ContextConfiguration(classes = [TestConfig::class])
class CustomerServiceTestIT : DefaultTestValues() {

    @Autowired
    lateinit var customerService: CustomerService

    @get:Rule
    var expectedEx: ExpectedException = ExpectedException.none()

    @Test
    fun `should find customers by CPF`() {

        var gallileu:Customer = generatedGalileuCustomer()
        var newton:Customer = generatedNewtonCustomer()

        var gallileuFound:Optional<Customer> = customerService.findByCPF(gallileu.cpf)
        var newtonFound: Optional<Customer> = customerService.findByCPF(newton.cpf)

        Assert.assertTrue(gallileuFound.isPresent);
        Assert.assertTrue(newtonFound.isPresent);
        Assert.assertTrue(ReflectionEquals(gallileu,"phones").matches(gallileuFound.get()))
        Assert.assertTrue(ReflectionEquals(newton,"phones").matches(newtonFound.get()))
        Assert.assertFalse(ReflectionEquals(newton,"phones").matches(gallileuFound.get()))
        Assert.assertFalse(ReflectionEquals(gallileu,"phones").matches(newtonFound.get()))
    }

    @Test
    fun `should find customers by id`() {

        var gallileu:Customer = generatedGalileuCustomer()
        var newton:Customer = generatedNewtonCustomer()

        var gallileuFound:Optional<Customer> = gallileu.id?.let { customerService.findById(it) } ?: run { Optional.empty()}
        var newtonFound: Optional<Customer> = newton.id?.let { customerService.findById(it) } ?: run { Optional.empty()}

        Assert.assertTrue(gallileuFound.isPresent);
        Assert.assertTrue(newtonFound.isPresent);
        Assert.assertTrue(ReflectionEquals(gallileu,"phones").matches(gallileuFound.get()))
        Assert.assertTrue(ReflectionEquals(newton,"phones").matches(newtonFound.get()))
        Assert.assertFalse(ReflectionEquals(newton,"phones").matches(gallileuFound.get()))
        Assert.assertFalse(ReflectionEquals(gallileu,"phones").matches(newtonFound.get()))
    }

    @Test
    fun `should throw an exception if there is already a customer with the same CPF`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_ALREADY_IN_USE)
        }

        var gallileu:Customer = generatedGalileuCustomer()
        customerService.create(gallileu)
    }

    @Test
    fun `should throw an exception if there is already a customer with the same id`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_ID_INVALID)
        }

        var gallileu:Customer = generatedGalileuCustomer()
        gallileu.cpf = CPF_VALID
        customerService.create(gallileu)
    }

    @Test
    fun `should able to save a customer and search for him`() {

        var marieCurie:Customer = generatedCurieCustomer()
        marieCurie.id = null
        customerService.create(marieCurie)
        var marieCurieFoundCPF:Optional<Customer> = customerService.findByCPF(marieCurie.cpf)
        var marieCurieFoundID:Optional<Customer> = marieCurie.id?.let { customerService.findById(it) } ?: run { Optional.empty()}

        Assert.assertTrue(marieCurieFoundCPF.isPresent);
        Assert.assertTrue(marieCurieFoundID.isPresent);
        Assert.assertTrue(ReflectionEquals(marieCurie,"phones","id").matches(marieCurieFoundCPF.get()))
        Assert.assertTrue(ReflectionEquals(marieCurie,"phones","id").matches(marieCurieFoundID.get()))
        Assert.assertTrue(ReflectionEquals(marieCurieFoundCPF.get(),"phones").matches(marieCurieFoundID.get()))
    }
}