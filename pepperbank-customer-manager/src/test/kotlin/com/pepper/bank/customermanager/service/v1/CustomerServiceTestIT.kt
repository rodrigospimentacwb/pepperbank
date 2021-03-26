package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.config.TestConfig
import com.pepper.bank.handler.exception.CustomerValidationException
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.repository.commons.CustomerRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.internal.matchers.apachecommons.ReflectionEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.*
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
        Sql("/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD))
@AutoConfigureTestDatabase(replace= Replace.NONE)
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
    fun `should be able to save a customer, search for it and delete it if not associate with an account`() {

        var marieCurie:Customer = generatedCurieCustomer()
        marieCurie.id = null
        marieCurie = customerService.create(marieCurie)
        var marieCurieFoundCPF:Optional<Customer> = customerService.findByCPF(marieCurie.cpf)
        var marieCurieFoundID:Optional<Customer> = marieCurie.id?.let { customerService.findById(it) } ?: run { Optional.empty()}

        Assert.assertTrue(marieCurieFoundCPF.isPresent);
        Assert.assertTrue(marieCurieFoundID.isPresent);
        Assert.assertTrue(ReflectionEquals(marieCurie,"phones","id").matches(marieCurieFoundCPF.get()))
        Assert.assertTrue(ReflectionEquals(marieCurie,"phones","id").matches(marieCurieFoundID.get()))
        Assert.assertTrue(ReflectionEquals(marieCurieFoundCPF.get(),"phones").matches(marieCurieFoundID.get()))
        customerService.delete(marieCurie)
        marieCurieFoundCPF = customerService.findByCPF(marieCurie.cpf)
        Assert.assertFalse(marieCurieFoundCPF.isPresent);
    }

    @Test
    fun `should allow editing customer`() {
        var newEmail:String = "changed.email@test.com"
        var gallileu:Customer = generatedGalileuCustomer()
        var gallileuFound:Optional<Customer> = gallileu.id?.let { customerService.findById(it) } ?: run { Optional.empty()}
        gallileuFound.get().email = newEmail
        customerService.update(gallileuFound.get())
        var gallileuFoundAltered:Optional<Customer> = gallileu.id?.let { customerService.findById(it) } ?: run { Optional.empty()}
        Assert.assertEquals(gallileuFoundAltered.get().email,newEmail)
    }

    @Test
    fun `should must not allow alteration of CPF`() {
        with(expectedEx) {
            expect(CustomerValidationException::class.java)
            expectMessage(MESSAGE.CUSTOMER_CPF_CHANGED)
        }
        var gallileu:Optional<Customer> = customerService.findByCPF(DefaultTestValues.GALILEU_CPF)
        gallileu.get().cpf = "01234567890"
        customerService.update(gallileu.get())
    }

    @Test
    fun `deve testar edicao de telefone`() {

    }

    @Test
    fun `deve testar exlucao de cliente`() {

    }

    @Test
    fun `deve testar exlucao de telefone`() {

    }

    @Test
    fun `deve testar inlusao de telefone`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por nome`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por nome sem nenhum resultado`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por cpf`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por cpf sem nenhum resultado`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por ddd e telefone`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por  ddd + telefone sem nenhum resultado`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por id`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por id sem nenhum resultado`() {

    }

    @Test
    fun `deve testar filtro de busca pessoa por ddd + telefone`() {

    }

    @Test
    fun `deve testar api busca cliente`() {

    }

    @Test
    fun `deve testar api de inclusao de cliente`() {

    }

    @Test
    fun `deve testar api de edicao de cliente`() {

    }

    @Test
    fun `deve testar api de exclusao de cliente`() {

    }

    @Test
    fun `deve testar api de inclusao de telefone`() {

    }

    @Test
    fun `deve testar api de edicao de telefone`() {

    }

    @Test
    fun `deve testar api de exclusao de telefone`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de busca de cliente`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de edicao de cliente`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de inclusao de cliente`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de exclusao de cliente`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de exclusao de telefone`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de inclusao de telefone`() {

    }

    @Test
    fun `deve testar validacao de chamada da api de edicao de telefone`() {

    }

    @Test
    fun `deve subir erro se exlucao de cliente com conta`() {

    }
}