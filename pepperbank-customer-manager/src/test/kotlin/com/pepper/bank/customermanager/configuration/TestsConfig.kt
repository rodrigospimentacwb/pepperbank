package com.pepper.bank.customermanager.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@ComponentScan(basePackages = ["com.pepper.bank.customermanager.service"
                              ,"com.pepper.bank.handler"
                              ,"com.pepper.bank.customermanager.controller"])
@EntityScan(basePackages = ["com.pepper.bank.model.commons"])
@EnableJpaRepositories(basePackages = ["com.pepper.bank.customermanager.repository"])
class TestsConfig