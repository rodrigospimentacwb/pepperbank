package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.accountmanager.configuration.TestsConfig
import com.pepper.bank.accountmanager.exception.AccountValidationException
import com.pepper.bank.accountmanager.repository.AccountRepository
import com.pepper.bank.api.customer.v1.CustomerApi
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.Optional
import com.pepper.bank.accountmanager.constants.AccountServiceMessage.Companion as MESSAGE

@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestsConfig::class])
class AccountServiceTest: DefaultTestValues() {

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var accountSevice: AccountService

    @MockBean
    lateinit var customerApi: CustomerApi

    @Test
    fun `should generate exception if the account number already exists more than 10`() {
        val account = generateGalileuAccountTest()
        Mockito.`when`(accountRepository.findByAgencyAndAccountNumber(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(account))

        val ex:AccountValidationException = Assert.assertThrows(
            AccountValidationException::class.java
        ) { accountSevice.generateNewAccountNumber(AGENCY) }
        Assert.assertTrue(ex.message.equals(MESSAGE.AGENCY_ACCOUNT_INVALD))
    }

    @Test
    fun `should generate account number`() {
        Mockito.`when`(accountRepository.findByAgencyAndAccountNumber(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        println(accountSevice.generateNewAccountNumber(AGENCY))
    }

    @Test
    fun `should validate when creating a new account if the agency has not been informed`() {
        Mockito.`when`(accountRepository.findByAgencyAndAccountNumber(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        val account = generateGalileuAccountTest()
        account.agency = ""

        val ex:AccountValidationException = Assert.assertThrows(
            AccountValidationException::class.java
        ) { accountSevice.validateNewAccount(account) }
        Assert.assertTrue(ex.message.equals(MESSAGE.AGENCY_INVALD))
    }

    @Test
    fun `should validate when creating a new account if the account number was not informed`() {
        Mockito.`when`(accountRepository.findByAgencyAndAccountNumber(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        val account = generateGalileuAccountTest()
        account.accountNumber = ""

        val ex:AccountValidationException = Assert.assertThrows(
            AccountValidationException::class.java
        ) { accountSevice.validateNewAccount(account) }
        Assert.assertTrue(ex.message.equals(MESSAGE.ACCOUNT_INVALD))
    }

    @Test
    fun `deve lancar execao se cliente não encontrado`() {
//        with(expectedEx) {
//            expect(AccountValidationException::class.java)
//            expectMessage(MESSAGE.CUSTOMER_NOT_FOUND)
//        }
//        val uuidCustomer:UUID = UUID.randomUUID()
//        Mockito.`when`(customerApi.getCustomerById(uuidCustomer.toString())).thenReturn(null)
//        accountSevice.ifExistsCustomer(uuidCustomer)
    }

    @Test
    fun `should throw exception if customer does not exist`() {
//        with(expectedEx) {
//            expect(AccountValidationException::class.java)
//            expectMessage(MESSAGE.ACCOUNT_INVALD)
//        }
    }

    @Test
    fun `deve subir excecao caso cliente ja tenha conta e tente criar uma nova`() {

    }

    @Test
    fun `deve salvar nova conta para cliente existente retornando identificador da agencia e conta`() {
        //Randomizar agencia e conta, verificar se ja não existe
    }

    @Test
    fun `deve buscar conta pelo UUID do cliente`() {
        //Randomizar agencia e conta, verificar se ja não existe
    }

    @Test
    fun `deve buscar conta pelo CPF do cliente`() {
        //Randomizar agencia e conta, verificar se ja não existe
    }
}