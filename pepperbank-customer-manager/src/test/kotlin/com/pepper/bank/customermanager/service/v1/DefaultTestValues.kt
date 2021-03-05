package com.pepper.bank.customermanager.service.v1

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
    }
}