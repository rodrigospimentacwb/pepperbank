package com.pepper.bank.accountmanager

import com.pepper.bank.accountmanager.service.v1.AccountService
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.net.InetAddress
import javax.annotation.PostConstruct

@SpringBootApplication
class AccountManagerApplication

private val logger = LogManager.getLogger(AccountManagerApplication::class.java)

fun main(args: Array<String>) {
    val app = runApplication<AccountManagerApplication>(*args)

    val applicationName = app.environment.getProperty("spring.application.name")
    val contextPath = app.environment.getProperty("server.servlet.context-path")
    val port = app.environment.getProperty("server.port")
    val hostAddress = InetAddress.getLocalHost().hostAddress

    logger.info("""|
                   |------------------------------------------------------------
                   |Application '$applicationName' is running! Access URLs:
                   |   Local:      http://127.0.0.1:$port$contextPath
                   |   External:   http://$hostAddress:$port$contextPath
                   |------------------------------------------------------------""".trimMargin())
}
