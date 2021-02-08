package com.pepper.bank.customermanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CustomerManagerApplication

fun main(args: Array<String>) {
    runApplication<CustomerManagerApplication>(*args)
}
