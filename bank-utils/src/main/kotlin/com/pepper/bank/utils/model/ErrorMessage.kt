package com.pepper.bank.utils.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ErrorMessage(
    @JsonFormat(shape = JsonFormat.Shape.STRING,
        pattern = "yy-MM-dd HH:mm:ss,SSS")
    var dateTime: LocalDateTime,
    var status: Int,
    val error:String,
    val message:String,
    val path: String,
    val method: String)