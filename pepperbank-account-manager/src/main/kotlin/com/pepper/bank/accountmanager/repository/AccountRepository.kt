package com.pepper.bank.accountmanager.repository

import com.pepper.bank.model.commons.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface AccountRepository : CrudRepository<Customer, UUID> {

}