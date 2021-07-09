package com.pepper.bank.accountmanager.repository

import com.pepper.bank.model.commons.Account
import com.pepper.bank.model.commons.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface AccountRepository : CrudRepository<Account, UUID> {
    fun findByAgencyAndAccountNumber(agency:String, account:String): Optional<Account>
}