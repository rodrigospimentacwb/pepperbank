package com.pepper.bank.api.dto.customer

import java.util.UUID

class PhoneTO(var id: UUID?, var ddd: String?, var phone: String?){

    constructor(ddd: String, phone: String) : this(null, ddd, phone) {
        ddd.also { this.ddd = it }
        phone.also { this.phone = it }
    }
}