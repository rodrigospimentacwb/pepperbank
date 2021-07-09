package com.pepper.bank.api.dto.account

import java.time.LocalDateTime
import java.util.UUID
import javax.validation.constraints.NotNull

class AccountTO(
    var id: UUID? = null,
    var agency: String = "1234",
    var accountNumber: String = "",
    var customerId: UUID? = null,
    var creation: LocalDateTime? = null
)