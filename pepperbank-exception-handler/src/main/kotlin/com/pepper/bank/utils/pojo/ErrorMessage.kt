package com.pepper.bank.utils.pojo

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

open class ErrorMessage(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd HH:mm:ss,SSS")
    var dateTime: LocalDateTime,
    var status: Int,
    val error:String,
    val message:String,
    val path: String,
    val method: String
)