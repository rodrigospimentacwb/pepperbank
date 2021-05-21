package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.model.commons.Account
import com.pepper.bank.model.commons.Customer
import com.pepper.bank.model.commons.Phone
import com.pepperbank.utils.converters.DateTimeConverter
import java.time.LocalDateTime
import java.util.UUID

open class DefaultTestValues {

    companion object {
        const val GALILEU_UUID = "57d0169a-587f-4971-b603-8b7c3fe52739"
        const val NEWTON_UUID = "55da4b84-b983-49d2-8844-b61ff7c679a2"
        const val ACCOUNT_ID_GALLILEU = "e57f7b06-b706-11eb-8529-0242ac130003"
        const val ACCOUNT_ID_NEWTON = "55da4b84-b983-49d2-8844-b61ff7c679a2"
        const val ACCOUNT_NUMBER_GALLILEU = "5678"
        const val ACCOUNT_NUMBER_NEWTON = "9123"
        const val AGENCY = "1234"
    }

    protected fun generateGalileuAccountTest(): Account = Account(
        id = UUID.fromString(ACCOUNT_ID_GALLILEU),
        agency = AGENCY,
        number = ACCOUNT_NUMBER_GALLILEU,
        customerId = UUID.fromString(GALILEU_UUID),
        creation = LocalDateTime.now())

    protected fun generateNewtonAccountTest(): Account = Account(
        id = UUID.fromString(ACCOUNT_ID_NEWTON),
        agency = AGENCY,
        number = ACCOUNT_NUMBER_NEWTON,
        customerId = UUID.fromString(NEWTON_UUID),
        creation = LocalDateTime.now())
}