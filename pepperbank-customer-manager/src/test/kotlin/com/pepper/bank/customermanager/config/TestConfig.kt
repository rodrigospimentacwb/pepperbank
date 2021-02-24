package com.pepper.bank.customermanager.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@Configuration
@ComponentScan(basePackages = ["com.pepper.bank.customermanager.service.v1"])
@EntityScan(basePackages = ["com.pepper.bank.model.commons"])
@EnableJpaRepositories(basePackages = ["com.pepper.bank.repository.commons"])
class TestConfig