package com.pepper.bank.repository.commons

import com.pepper.bank.domain.commons.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerRepository: CrudRepository<Customer, UUID> {

}