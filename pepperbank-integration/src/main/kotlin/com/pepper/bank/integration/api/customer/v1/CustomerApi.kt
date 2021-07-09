package com.pepper.bank.integration.api.customer.v1

import com.pepper.bank.api.dto.customer.CustomerTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET

@FeignClient(name = "customerManager", url = "\${pepper.bank.customer.manager}")
interface CustomerApi {

    @RequestMapping(method = [GET], value = ["/api/v1/customers/{id}"])
    fun getCustomerById(@PathVariable id: String): CustomerTO

}