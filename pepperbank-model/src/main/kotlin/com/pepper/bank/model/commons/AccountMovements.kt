package com.pepper.bank.model.commons

import org.hibernate.annotations.GenericGenerator
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "account_movements")
data class AccountMovements (
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    var account:Account,
    @Column(name = "operation", length =1, nullable = false)
    var operation:String,
    @Column(name = "dateTime", nullable = false)
    var dateTime:LocalDateTime
)