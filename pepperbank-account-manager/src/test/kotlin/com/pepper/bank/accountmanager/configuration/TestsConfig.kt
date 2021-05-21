package com.pepper.bank.accountmanager.configuration

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories


@Configuration
@ComponentScan(basePackages = ["com.pepper.bank.accountmanager.service"
                              ,"com.pepper.bank.handler"
                              ,"com.pepper.bank.accountmanager.controller"])
@EntityScan(basePackages = ["com.pepper.bank.model.commons"])
@EnableJpaRepositories(basePackages = ["com.pepper.bank.accountmanager.repository"])
class TestsConfig