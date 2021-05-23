package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import com.pepper.bank.api.dto.customer.PhoneTO
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.model.commons.Phone
import com.pepperbank.utils.converters.DateTimeConverter
import java.time.LocalDate
import java.util.UUID

open class DefaultTestValues {

    companion object {
        const val BIRTHDATE: String = "08/03/1984"
        const val PHONE_1_DDD = "41"
        const val PHONE_1_NUMBER = "99998888"
        const val PHONE_2_DDD = "45"
        const val PHONE_2_NUMBER = "32324343"
        const val NAME: String = "João dos Santos"
        const val EMAIL: String = "joao.santos@teste.com"
        const val CPF_VALID: String = "50217277020"
        const val CPF_INVALID: String = "04714036952"
        const val NAME_INVALID_MORE_100_CHACARACTERS: String = "Jose da Silva Franciso dos Santos e Alvez de Albuquerque Arantes Silva Oliveira Martins Marco Vasques Coimbra"

        const val GALILEU_UUID:String = "57d0169a-587f-4971-b603-8b7c3fe52739"
        const val GALILEU_NAME:String = "Galileu Galilei"
        const val GALILEU_CPF:String = "44265852017"
        const val GALILEU_EMAIL:String = "galileu.galilei@astronomia.org"
        const val GALILEU_BIRTHDATE:String = "08/03/1984"

        const val NEWTON_UUID:String = "55da4b84-b983-49d2-8844-b61ff7c679a2"
        const val NEWTON_NAME:String = "Isaac Newton"
        const val NEWTON_CPF:String = "17193944070"
        const val NEWTON_EMAIL:String = "isaac.newton@gravitacao.com"
        const val NEWTON_BIRTHDATE:String = "07/06/1990"

        const val CURIE_UUID:String = "b4ebff38-0daa-4789-97e9-776f1bb8be6e"
        const val CURIE_NAME:String = "Marie Curie"
        const val CURIE_CPF:String = "95190371018"
        const val CURIE_EMAIL:String = "marie.curie@radio.or"
        const val CURIE_BIRTHDATE:String = "05/08/2000"
        const val INVALID_UUID:String = "1d15w151d5w5d15w1d"
    }

    protected fun generatedTestCustomer(id: UUID?,
                                        cpf:String,
                                        email:String,
                                        name:String,
                                        birthDate:LocalDate?,
                                        phones:List<Phone>?): Customer = Customer(
        id = id,
        cpf = cpf,
        email = email,
        name = name,
        birthDate = birthDate,
        phones = phones)

    protected fun generatedTestCustomerTO(id: UUID?,
                                        cpf:String,
                                        email:String,
                                        name:String,
                                        birthDate:LocalDate?,
                                        phones:List<PhoneTO>?): CustomerTO = CustomerTO(
        id = id,
        cpf = cpf,
        email = email,
        name = name,
        birthDate = birthDate,
        phones = phones
    )

    protected fun generatedTestCustomerDefault(): Customer = generatedTestCustomer(
        null,
        cpf = CPF_VALID,
        email = EMAIL,
        name = NAME,
        birthDate = DateTimeConverter.convertStringToLocalDate(Companion.BIRTHDATE),
        phones = arrayListOf<Phone>(
            Phone(PHONE_1_DDD, PHONE_1_NUMBER),
            Phone(PHONE_2_DDD, PHONE_2_NUMBER)
        ))

    protected fun generatedGalileuCustomer(): Customer = generatedTestCustomer(
        UUID.fromString(GALILEU_UUID),
        GALILEU_CPF,
        GALILEU_EMAIL,
        GALILEU_NAME,
        DateTimeConverter.convertStringToLocalDate(GALILEU_BIRTHDATE),
        arrayListOf<Phone>(
            Phone(PHONE_1_DDD, PHONE_1_NUMBER),
            Phone(PHONE_2_DDD, PHONE_2_NUMBER)
        )
    )

    protected fun generatedGalileuCustomerTO(): CustomerTO = generatedTestCustomerTO(
        UUID.fromString(GALILEU_UUID),
        GALILEU_CPF,
        GALILEU_EMAIL,
        GALILEU_NAME,
        DateTimeConverter.convertStringToLocalDate(GALILEU_BIRTHDATE),
        arrayListOf<PhoneTO>(
            PhoneTO(PHONE_1_DDD, PHONE_1_NUMBER),
            PhoneTO(PHONE_2_DDD, PHONE_2_NUMBER)
        )
    )

    protected fun generatedNewtonCustomer(): Customer = generatedTestCustomer(
        UUID.fromString(NEWTON_UUID),
        NEWTON_CPF,
        NEWTON_EMAIL,
        NEWTON_NAME,
        DateTimeConverter.convertStringToLocalDate(NEWTON_BIRTHDATE),
        arrayListOf<Phone>(
            Phone(PHONE_1_DDD, PHONE_1_NUMBER),
            Phone(PHONE_2_DDD, PHONE_2_NUMBER)
        )
    )

    protected fun generatedCurieCustomer(): Customer = generatedTestCustomer(
        UUID.fromString(CURIE_UUID),
        CURIE_CPF,
        CURIE_EMAIL,
        CURIE_NAME,
        DateTimeConverter.convertStringToLocalDate(CURIE_BIRTHDATE),
        arrayListOf<Phone>(
            Phone(PHONE_1_DDD, PHONE_1_NUMBER),
            Phone(PHONE_2_DDD, PHONE_2_NUMBER)
        )
    )

    protected fun generateListCustomers(): List<Customer> =
        listOf(generatedCurieCustomer(),generatedGalileuCustomer(), generatedNewtonCustomer())
}