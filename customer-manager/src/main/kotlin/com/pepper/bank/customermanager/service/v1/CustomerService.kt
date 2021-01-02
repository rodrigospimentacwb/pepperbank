package com.pepper.bank.customermanager.service.v1

import com.pepper.bank.domain.commons.Customer
import java.util.*

interface CustomerService {
    fun create(customer:Customer): Customer
    fun getByID(id:String):Customer
    fun getByCPF(cpf:String):Customer?
    fun update(customer:Customer)
    fun delete(id:UUID)
    fun getAll():List<Customer>
}