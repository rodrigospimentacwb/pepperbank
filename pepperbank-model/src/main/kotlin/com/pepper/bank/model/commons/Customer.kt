package com.pepper.bank.model.commons

import org.hibernate.annotations.GenericGenerator
import org.hibernate.validator.constraints.br.CPF
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "customer")
class Customer(
    @Id
    @field:[NotNull]
    @Column(name = "id", nullable = false)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    var id: UUID? = null,
    @Column(name = "name", length = 100, nullable = false)
    @field:[NotBlank Size(min = 2, max = 100, message = "Minimo 2 maximo 100 caracteres")]
    var name: String = "",
    @field:[CPF(message = "CPF inválido")]
    @Column(name = "cpf", length = 11, nullable = false)
    var cpf: String = "",
    @field:[Email(message = "E-mail inválido")]
    @Column(name = "email", length = 125, nullable = true)
    var email: String = "",
    @Column(name = "birthdate", nullable = false)
    var birthDate: LocalDate? = null,
    @OneToMany(mappedBy = "customer")
    var phones: List<Phone>? = null
)