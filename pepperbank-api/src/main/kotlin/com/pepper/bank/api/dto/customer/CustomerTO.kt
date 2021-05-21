package com.pepper.bank.api.dto.customer

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.pepperbank.utils.serializers.LocalDateDeserializer
import com.pepperbank.utils.serializers.LocalDateSerializer
import java.time.LocalDate
import java.util.UUID

class CustomerTO(){
    var id: UUID? = null
    var name: String? = null
    var cpf: String? = null
    var email: String? = null
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    var birthDate: LocalDate? = null
}