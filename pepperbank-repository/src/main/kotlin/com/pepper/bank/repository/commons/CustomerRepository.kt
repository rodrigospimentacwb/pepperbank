package com.pepper.bank.repository.commons

import com.pepper.bank.model.commons.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CustomerRepository : CrudRepository<Customer, UUID> {
    fun findByCPF(cpfValid: String): Optional<Customer>
}