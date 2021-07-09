package com.pepper.bank.api.v1

import com.pepper.bank.api.dto.account.AccountTO
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus

@FeignClient(name = "accountManager", url = "\${pepper.bank.account.manager}")
interface AccountApi {

    @ResponseStatus(OK)
    @GetMapping(
        value = ["/api/v1/accounts"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun listAllAccounts(): List<AccountTO>

}