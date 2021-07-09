package com.pepper.bank.accountmanager.controller.v1

import com.pepper.bank.accountmanager.service.v1.AccountService
import com.pepper.bank.api.dto.account.AccountTO
import com.pepper.bank.api.v1.AccountApi
import com.pepperbank.utils.converters.JsonConverter
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    private val accountService: AccountService
) : AccountApi {

    override fun listAllAccounts(): List<AccountTO> {
        return JsonConverter.convert(accountService.getAll(), AccountTO::class.java)
    }
}