package com.pepper.bank.customermanager.controller.v1

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.pepper.bank.customermanager.config.v1.TestConfig
import com.pepper.bank.customermanager.service.v1.CustomerService
import com.pepper.bank.customermanager.service.v1.DefaultTestValues
import com.pepper.bank.model.commons.Customer
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import liquibase.pro.packaged.id

import liquibase.pro.packaged.an
import org.springframework.test.web.servlet.MvcResult


@RunWith(value = SpringRunner::class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:application-test.properties")
@SqlGroup(
    Sql("/load-database.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
)
@ContextConfiguration(classes = [TestConfig::class])
@AutoConfigureMockMvc
class CustomerControllerTestIT : DefaultTestValues() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var customerService: CustomerService

    @get:Rule
    var expectedEx: ExpectedException = ExpectedException.none()

    val baseUrl: String = "/api/v1/customers"

    fun extractCustomer(resultActions:ResultActions): Customer {
        val result: MvcResult = resultActions.andReturn()
        val contentAsString = result.response.contentAsString
        return jacksonObjectMapper().readValue(contentAsString,Customer::class.java)
    }

    @Test
    fun `should search for customer by id`() {
        mockMvc.perform(
            get("$baseUrl/{id}", GALILEU_UUID)
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(GALILEU_NAME))
            .andExpect(jsonPath("$.id").value(GALILEU_UUID))
    }

    @Test
    fun `should return an exception of not found if id not exists in data base`() {
        mockMvc.perform(
            get("$baseUrl/{id}", CURIE_UUID)
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return an exception of bad request if id is invalid`() {
        mockMvc.perform(
            get("$baseUrl/{id}", "assas")
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Invalid format id: assas"))
    }

    @Test
    fun `should return an exception of bad request if id is empty`() {
        mockMvc.perform(
            get("$baseUrl/{id}", "")
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should search for customer by cpf if CPF`() {
        mockMvc.perform(
            get("$baseUrl/cpf/{cpf}", GALILEU_CPF)
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(GALILEU_NAME))
            .andExpect(jsonPath("$.id").value(GALILEU_UUID))
            .andExpect(jsonPath("$.cpf").value(GALILEU_CPF))
    }

    @Test
    fun `should return an exception of bad request if CPF is invalid`() {
        mockMvc.perform(
            get("$baseUrl/cpf/{cpf}", CPF_INVALID)
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("CustomerValidationException"))
            .andExpect(jsonPath("$.message").value("Customer CPF is invalid"))
    }

    @Test
    fun `should return an exception of bad request if CPF empty`() {
        mockMvc.perform(
            get("$baseUrl/cpf/{cpf}", "")
                .contentType("application/json")
        )
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("BadRequestException"))
            .andExpect(jsonPath("$.message").value("Invalid format id: cpf"))
    }

    @Test
    fun `should insert a customer and pick it up`(){

        var customerCurie = generatedCurieCustomer()
        customerCurie.id = null
        var resultActions:ResultActions = mockMvc.perform(
            post("$baseUrl")
                .contentType("application/json")
                .content(jacksonObjectMapper().writeValueAsString(customerCurie))
        )
            .andDo(print())
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(CURIE_NAME))
            .andExpect(jsonPath("$.cpf").value(CURIE_CPF))
            .andExpect(jsonPath("$.id").isNotEmpty)

        var customer = extractCustomer(resultActions)

        mockMvc.perform(
            get("$baseUrl/{id}", customer.id)
                .contentType("application/json")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(CURIE_NAME))
            .andExpect(jsonPath("$.cpf").value(CURIE_CPF))
    }

    @Test
    fun `should throw exception if id is filled in the insertion`(){

        var customerCurie = generatedCurieCustomer()
        mockMvc.perform(
            post("$baseUrl")
                .contentType("application/json")
                .content(jacksonObjectMapper().writeValueAsString(customerCurie)))
            .andDo(print())
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.error").value("CustomerValidationException"))
            .andExpect(jsonPath("$.message").value("Customer´s id is invalid"))
    }

//    @Test
//    fun `should be`(){
//
//        mockMvc.perform(
//            post(ApisUrl.FIND_BY_ID)
//                .contentType("application/json")
//                .content(jacksonObjectMapper().writeValueAsString("")))
//            .andDo(print())
//            .andExpect(status().isNotFound)
//            .andExpect(jsonPath("$.message").value("Hello World!!!"))
//    }


}