package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.accountmanager.configuration.TestsConfig
import com.pepper.bank.accountmanager.repository.AccountRepository
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner
import java.util.Optional
import java.util.UUID
import com.pepper.bank.accountmanager.constants.AccountServiceMessage.Companion as MESSAGE

@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestsConfig::class])
class AccountServiceTest: DefaultTestValues() {

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var accountSevice: AccountService

    @Rule
    @JvmField
    var expectedEx: ExpectedException = ExpectedException.none()

    @Test
    fun `deve gerar execao se o numero da conta ja existir mais de 10 tentativas`() {
        with(expectedEx) {
            expect(com.pepper.bank.handler.exception.AccountValidationException::class.java)
            expectMessage(MESSAGE.AGENCY_ACCOUNT_INVALD)
        }

        var account = generateGalileuAccountTest()
        Mockito.`when`(accountRepository.findByAgencyAndAccount(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.of(account))
        accountSevice.generateNewAccountNumber(AGENCY)
    }

    @Test
    fun `deve gerar numero da conta`() {
        Mockito.`when`(accountRepository.findByAgencyAndAccount(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        println(accountSevice.generateNewAccountNumber(AGENCY))
    }

    @Test
    fun `deve validar na criacao de nova conta se foi informado a agencia`() {
        with(expectedEx) {
            expect(com.pepper.bank.handler.exception.AccountValidationException::class.java)
            expectMessage(MESSAGE.AGENCY_INVALD)
        }
        Mockito.`when`(accountRepository.findByAgencyAndAccount(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        var account = generateGalileuAccountTest()
        account.agency = ""
        accountSevice.validateNewAccount(account)
    }

    @Test
    fun `deve validar na criacao de nova conta se foi informado o numero da conta`() {
        with(expectedEx) {
            expect(com.pepper.bank.handler.exception.AccountValidationException::class.java)
            expectMessage(MESSAGE.ACCOUNT_INVALD)
        }
        Mockito.`when`(accountRepository.findByAgencyAndAccount(Mockito.anyString(),Mockito.anyString())).thenReturn(Optional.empty())
        var account = generateGalileuAccountTest()
        account.number = ""
        accountSevice.validateNewAccount(account)
    }

    @Test
    fun `deve subir excecao caso cliente nao exista`() {

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