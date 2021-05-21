package com.pepper.bank.accountmanager.service.v1

import com.pepper.bank.accountmanager.repository.AccountRepository
import com.pepper.bank.handler.exception.AccountValidationException
import com.pepper.bank.model.commons.Account
import org.springframework.stereotype.Service
import java.util.Optional
import kotlin.random.Random
import com.pepper.bank.accountmanager.constants.AccountServiceMessage.Companion as MESSAGE

@Service
class AccountService(val accountRepository: AccountRepository) {

    @Throws(AccountValidationException::class)
    fun ifExistsAgencyAccount(agency:String, account:String){
        if(findByAgencyAndAccount(agency, account).isPresent){
            throw AccountValidationException(MESSAGE.AGENCY_ACCOUNT_INVALD)
        }
    }

//    @Throws(AccountValidationException::class)
//    fun ifExistsCustomer(customerId:UUID){
//
//        if(findByAgencyAndAccount(agency, account).isPresent){
//            throw AccountValidationException(MESSAGE.AGENCY_ACCOUNT_INVALD)
//        }
//    }

    fun findByAgencyAndAccount(agency:String, account:String): Optional<Account> =
        accountRepository.findByAgencyAndAccount(agency, account)

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
            account.number.isNullOrBlank() -> throw AccountValidationException(MESSAGE.ACCOUNT_INVALD)
        }
        ifExistsAgencyAccount(account.agency, account.number)
    }

    fun create(account:Account){
        validateNewAccount(account)

    }
}
