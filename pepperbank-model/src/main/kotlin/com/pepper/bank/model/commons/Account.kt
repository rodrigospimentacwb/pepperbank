package com.pepper.bank.model.commons

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull


@Entity
@Table(name = "account")
class Account(
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "agency", length = 5, nullable = false)
    var agency: String = "1234",
    @Column(name = "number", length = 10, nullable = false)
    var accountNumber: String = "",
    @Column(name = "customer_id", nullable = false)
    var customerId: UUID? = null,
    @Column(name = "creation", nullable = false)
    var creation: LocalDateTime = LocalDateTime.now()
)