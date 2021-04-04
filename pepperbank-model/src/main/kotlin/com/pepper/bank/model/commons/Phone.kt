package com.pepper.bank.model.commons

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotNull

@Entity
class Phone(
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "ddd", length = 2, nullable = false)
    var ddd: String = "",
    @Column(name = "phone", length = 9, nullable = false)
    var phone: String = "",
    @ManyToOne
    @JoinColumn(name = "customer_id")
    @JsonIgnore
    var customer: Customer?
) {

    constructor(ddd: String, phone: String) : this(null, ddd, phone, null) {
        ddd.also { this.ddd = it }
        phone.also { this.phone = it }
    }
}