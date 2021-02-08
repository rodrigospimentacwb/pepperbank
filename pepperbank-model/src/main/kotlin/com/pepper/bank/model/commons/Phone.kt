package com.pepper.bank.model.commons

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Phone (
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "ddd", length = 2, nullable = false)
    var ddd:String = "",
    @Column(name = "phone", length = 9, nullable = false)
    var phone:String= "",
    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer:Customer
)