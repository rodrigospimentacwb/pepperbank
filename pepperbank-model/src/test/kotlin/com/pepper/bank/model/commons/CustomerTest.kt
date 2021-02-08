package com.pepper.bank.model.commons

import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class CustomerTest {

    companion object{

        @TestConfiguration
        open class CustomerTestConfiguration(){
            @Bean
            fun customer():Customer{
                return Customer()
            }
        }

        @Autowired
        lateinit var customer:Customer
    }

    @Test
    fun `should receive an instance with null id`(){
        customer = Customer()
        Assertions.assertTrue(customer.id == null)
    }

}