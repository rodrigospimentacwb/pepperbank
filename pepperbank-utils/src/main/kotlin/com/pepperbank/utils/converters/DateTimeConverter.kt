package com.pepperbank.utils.converters

import com.pepper.bank.handler.exception.FormatDateTimeException
import org.apache.logging.log4j.LogManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTimeConverter {

    companion object {

        private val logger = LogManager.getLogger(this::class.java)

        const val DATE_DEFAULT_FORMAT = "dd/MM/yyyy"

        fun convertStringToLocalDate(date: String, format: String = DATE_DEFAULT_FORMAT): LocalDate {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(format))
            } catch (e: Exception) {
                logger.error("Fail convert String date $date with format $format to LocalDate", e)
                throw FormatDateTimeException("Fail convert String date $date with format $format to LocalDate")
            }
        }
    }
}
