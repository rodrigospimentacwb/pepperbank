package com.pepper.bank.domain.commons

import org.hibernate.annotations.GenericGenerator
import org.hibernate.validator.constraints.br.CPF
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
data class Customer (
    @Id
    @NotNull
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: UUID? = null,
    @Column(name = "name", length = 100)
    @NotBlank
    @Size(min=2, max=100)
    val name: String = "",
    @CPF
    @Column(name = "cpf", length = 11)
    val cpf: String = ""
)