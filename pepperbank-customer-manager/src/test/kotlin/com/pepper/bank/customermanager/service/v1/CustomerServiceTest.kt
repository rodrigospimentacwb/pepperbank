package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.customermanager.service.v1.impl.CustomerServiceImpl
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.model.commons.Phone
import com.pepper.bank.repository.commons.CustomerRepository
import com.pepperbank.utils.converters.DateTimeConverter
import org.junit.Before
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(value = SpringRunner::class)
class CustomerServiceTest : DefaultTestValues() {

    @MockBean
    private lateinit var customerRepository: CustomerRepository

    private lateinit var customerService:CustomerService

    private lateinit var customer:Customer

    fun createDefaultCustomer(){
        customer.also {
            it.cpf = CPF_VALID
            it.email = EMAIL
            it.name = NAME
            it.birthDate = DateTimeConverter.convertStringToLocalDate(BIRTHDATE)
            it.phones = arrayListOf<Phone>(Phone(PHONE_1_DDD, PHONE_1_NUMBER),
                Phone(PHONE_2_DDD, PHONE_2_NUMBER))
        }
    }

    @Before
    fun setup(){
        var customerService = CustomerServiceImpl(customerRepository)
        createDefaultCustomer()
    }

    @Test
    fun `should save client in the repository`() {

    }
}