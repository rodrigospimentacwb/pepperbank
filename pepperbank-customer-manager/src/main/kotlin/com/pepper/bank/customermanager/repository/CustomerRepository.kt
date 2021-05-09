package com.pepper.bank.customermanager.repository

import com.pepper.bank.model.commons.Customer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface CustomerRepository : CrudRepository<Customer, UUID> {
    fun findByCpf(cpfValid: String): Optional<Customer>
}