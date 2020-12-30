package com.pepper.bank.customermanager.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@EntityScan(basePackages = [
    "com.pepper.bank.domain.commons"
])
@EnableJpaRepositories(basePackages = [
    "com.pepper.bank.repository.commons"
])
@ComponentScan(basePackages = [
    "com.pepper.bank.utils.handlers"
])
class LoadDependencies {
}