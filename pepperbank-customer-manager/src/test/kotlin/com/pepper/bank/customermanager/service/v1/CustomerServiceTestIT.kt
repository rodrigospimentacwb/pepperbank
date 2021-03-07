package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.config.TestConfig
import com.pepper.bank.model.commons.Customer
import org.h2.tools.Server
import org.junit.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import java.sql.SQLException
import java.util.Optional

@RunWith(value = SpringRunner::class)
@DataJpaTest
@TestPropertySource("/application-test.properties")
@SqlGroup(
        Sql("/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        Sql("/clean-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD))
@ContextConfiguration(classes = [TestConfig::class])
class CustomerServiceTestIT : DefaultTestValues() {

    @Autowired
    lateinit var customerService: CustomerService

    @Test
    fun `should teste`() {
        var customer:Optional<Customer> = customerService.findByCPF("04714036955")
        if(customer.isPresent){
            println("**************" + customer.get())
        }else
        {
            println("************** Não encontrado")
        }
    }

}