package com.pepper.bank.model.commons

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class AccountTest {

    companion object {

        @TestConfiguration
        open class AccountTestConfiguration() {
            @Bean
            fun account(): Account {
                return Account()
            }
        }

        @Autowired
        lateinit var account: Account
    }

    @Test
    fun `should receive an instance with null id`() {
        account = Account()
        Assertions.assertTrue(account.id == null)
    }
}