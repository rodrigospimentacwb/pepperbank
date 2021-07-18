package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.accountmanager.exception.AccountValidationException
import com.pepper.bank.accountmanager.exception.CustomFeignException
import com.pepper.bank.accountmanager.repository.AccountRepository
import com.pepper.bank.api.customer.v1.CustomerApi
import com.pepper.bank.api.dto.customer.CustomerTO
import com.pepper.bank.handler.exception.NotFoundException
import com.pepper.bank.model.commons.Account
import feign.FeignException
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.Optional
import java.util.UUID
import kotlin.random.Random
import com.pepper.bank.accountmanager.constants.AccountServiceMessage.Companion as MESSAGE

@Service
class AccountService(val accountRepository: AccountRepository) {

    private val logger = LogManager.getLogger(this::class.java)

    @Autowired
    lateinit var customerApi:CustomerApi

    fun getAll(): List<Account> {

        ifExistsCustomer(UUID.fromString("4105ff95-561c-4156-b9af-dcbc56638e96"))

        try {
            var accounts:List<Account> = accountRepository.findAll().filterIsInstance<Account>()
            return accounts.ifEmpty { throw NotFoundException(MESSAGE.NO_ACCOUNT_FOUND) }
        } catch (e: NotFoundException) {
            throw e
        } catch (e2: Exception) {
            throw RuntimeException()
        }
    }

    @Throws(AccountValidationException::class)
    fun ifExistsAgencyAccount(agency:String, account:String){
        if(findByAgencyAndAccount(agency, account).isPresent){
            throw AccountValidationException(MESSAGE.AGENCY_ACCOUNT_INVALD)
        }
    }

    @Throws(AccountValidationException::class)
    fun ifExistsCustomer(customerId:UUID){
        if(!findCustomer(customerId.toString()).isPresent){
            throw AccountValidationException(MESSAGE.CUSTOMER_NOT_FOUND)
        }
    }

    fun findByAgencyAndAccount(agency:String, account:String): Optional<Account> =
        accountRepository.findByAgencyAndAccountNumber(agency, account)

    fun generateNewAccountNumber(agency:String): String {
        var contMaxAttempts:Int = 0
        var account: String?
        while(true){
            account = Random.nextLong(99999).toString()
            try {
                ifExistsAgencyAccount(agency, account)
                return account
            } catch (ex: Exception){
                contMaxAttempts += 1
                if(contMaxAttempts >= 10){
                    throw ex
                }
            }
        }
    }

    @Throws(AccountValidationException::class)
    fun validateNewAccount(account:Account){
        when{
            account.agency.isNullOrBlank() -> throw AccountValidationException(MESSAGE.AGENCY_INVALD)
            account.accountNumber.isNullOrBlank() -> throw AccountValidationException(MESSAGE.ACCOUNT_INVALD)
            account.customerId == null -> throw AccountValidationException(MESSAGE.CUSTOMER_ID_INVALID)
        }
        ifExistsAgencyAccount(account.agency, account.accountNumber)
        account.customerId?.let { ifExistsCustomer(it) }
    }

    fun create(account:Account){
        validateNewAccount(account)
    }

    fun findCustomer(id:String): Optional<CustomerTO>{
        try {
            return Optional.of(customerApi.getCustomerById(id))
        } catch (f:FeignException){
            logger.error(CustomFeignException(f,"trying to call customer-manager"))
        } catch (e:Exception){
            logger.error("Error find customer",e.message)
        }
        return Optional.empty()
    }
}
