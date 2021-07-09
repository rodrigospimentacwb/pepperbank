package com.pepper.bank.model.commons

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "account_movement")
class AccountMovement(
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    var account: Account? = null,
    @Column(name = "operation", length = 1, nullable = false)
    var operation: String = "",
    @Column(name = "datetime", nullable = false)
    var dateTime: LocalDateTime = LocalDateTime.now(),
    @Column(name = "amount", nullable = false)
    var amount: BigDecimal = BigDecimal.ZERO
)