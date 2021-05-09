package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.accountmanager.configuration.TestsConfig
import com.pepper.bank.accountmanager.repository.AccountRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(value = SpringRunner::class)
@ContextConfiguration(classes = [TestsConfig::class])
class AccountServiceTest {

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun `deve validar dados obrigatorios para criacao de conta`() {

    }

    @Test
    fun `deve subir excecao caso cliente nao exista`() {

    }

    @Test
    fun `deve subir excecao caso cliente ja tenha conta e tente criar uma nova`() {

    }

    @Test
    fun `deve salvar nova conta para cliente existente`() {

    }
}