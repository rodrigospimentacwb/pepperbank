package com.pepper.bank.customermanager.constants

class CustomerServiceMessage {
    companion object {
        const val CUSTOMER_BIRTHDATE_NOT_BE_NULL = "Customer birth date cannot be null"
        const val CUSTOMER_BIRTHDATE_NOT_BE_GREATER_CURRENT_DATE = "BirthDate cannot be greater than the current date"
        const val CUSTOMER_NAME_NOT_BE_NULL_OR_BLANK = "Customer name cannot be null or empty"
        const val CUSTOMER_NAME_LONG_100_CHARACTERS = "Customer name cannot be longer than 100 characters"
        const val CUSTOMER_CPF_NOT_BE_NULL_OR_BLANK = "Customer CPF cannot be null or empty"
        const val CUSTOMER_CPF_INVALID = "Customer CPF is invalid"
        const val CUSTOMER_CPF_ALREADY_IN_USE = "Customer´s CPF is already in use"
        const val CUSTOMER_ID_INVALID = "Customer´s id is invalid"
        const val CUSTOMER_DELETE_ERROR = "Could not delete customer"
        const val CUSTOMER_CPF_CHANGED = "Customer CPF cannot be change"
        const val NO_CLIENT_FOUND = "No clients found"
        const val FAIL_PARSE_DTO_TO_ENTITY = "Fail to parse json DTO to Entity"
    }
}