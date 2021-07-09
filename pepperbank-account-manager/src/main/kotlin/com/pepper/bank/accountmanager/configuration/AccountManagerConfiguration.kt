package com.pepper.bank.accountmanager.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(
    basePackages = [
        "com.pepper.bank.model.commons"
    ]
)
@EnableJpaRepositories(
    basePackages = [
        "com.pepper.bank.accountmanager.repository"
    ]
)
@ComponentScan(
    basePackages = [
        "com.pepper.bank.handler"
    ]
)
@EnableFeignClients(
    basePackages = [
        "com.pepper.bank.api"
    ]
)
class AccountManagerConfiguration {
}