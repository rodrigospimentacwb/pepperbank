package com.pepper.bank.model.commons

import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
@Table(name = "account")
data class Account (
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "agency", length = 5, nullable = false)
    var agency:String = "",
    @Column(name = "account", length = 10, nullable = false)
    var account:String = "",
    @OneToOne
    @JoinColumn(name = "customer_id")
    var customer:Customer,
    @Column(name = "creation", nullable = false)
    var creation:LocalDateTime = LocalDateTime.now()
)