package com.pepperbank.utils.converters

import com.pepper.bank.utils.exception.FormatDateTimeException
import org.apache.logging.log4j.LogManager
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTimeConverter {

    companion object{

        private val LOG = LogManager.getLogger(this.javaClass)

        const val DATE_DEFAULT_FORMAT = "dd/MM/yyyy"

        fun convertStringToLocalDate(date: String, format:String = DATE_DEFAULT_FORMAT): LocalDate {
            try {
                return LocalDate.parse(date, DateTimeFormatter.ofPattern(format))
            }catch (ex:Exception){
                LOG.error("Exception error: [${ex.message}].", ex)
                throw FormatDateTimeException("Fail convert String date $date with format $format to LocalDate")
            }
        }
    }
}
