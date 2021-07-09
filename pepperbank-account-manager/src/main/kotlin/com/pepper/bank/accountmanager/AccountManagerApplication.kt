package com.pepper.bank.accountmanager

import com.pepper.bank.accountmanager.service.v1.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
class AccountManagerApplication

@Autowired
lateinit var accountService:AccountService

fun main(args: Array<String>) {
    runApplication<AccountManagerApplication>(*args)

    println(accountService.findCustomer("4105ff95-561c-4156-b9af-dcbc56638e96"));
}
