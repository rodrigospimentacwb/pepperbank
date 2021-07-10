package com.pepper.bank.accountmanager.configuration


import org.springframework.boot.autoconfigure.ImportAutoConfiguration
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.cloud.openfeign.FeignAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@EntityScan(basePackages = [
    "com.pepper.bank.model.commons"
])
@EnableJpaRepositories(basePackages = [
    "com.pepper.bank.accountmanager.repository"
])
@ComponentScan(basePackages = [
    "com.pepper.bank.handler",
    "com.pepper.bank.accountmanager.service"
])
@EnableFeignClients(basePackages = [
    "com.pepper.bank.api.customer"
])
@ImportAutoConfiguration(classes = [
    RibbonAutoConfiguration::class,
    FeignAutoConfiguration::class
])
class TestsConfig