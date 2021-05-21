package com.pepperbank.utils.converters

import com.pepper.bank.handler.exception.FormatDateTimeException
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateTimeConverterTest {

    private val DEFAULT_DATE: String = "08/03/1984"
    private val CUSTOM_FORMAT = "MM/dd/yyyy"
    private val CUSTOM_DATE_FORMAT = "03/08/1984"
    private val INVALID_DATE = "99/99/9999"
    private val INVALID_FORMAT = "YYYY/DD/MM"

    @Test
    fun `should convert the date to LocalDate with default format`() {
        var date: LocalDate = DateTimeConverter.convertStringToLocalDate(DEFAULT_DATE)
        val formatter = DateTimeFormatter.ofPattern(DateTimeConverter.DATE_DEFAULT_FORMAT)
        Assert.assertEquals(DEFAULT_DATE, date.format(formatter))
    }

    @Test
    fun `should convert the date to LocalDate with custom format`() {
        var date: LocalDate = DateTimeConverter.convertStringToLocalDate(CUSTOM_DATE_FORMAT, CUSTOM_FORMAT)
        val formatter = DateTimeFormatter.ofPattern(CUSTOM_FORMAT)
        Assert.assertEquals(CUSTOM_DATE_FORMAT, date.format(formatter))
    }

    @Test(expected = FormatDateTimeException::class)
    fun `should throw FormatDateTimeException exception with invalid date`() {
        DateTimeConverter.convertStringToLocalDate(INVALID_DATE, CUSTOM_FORMAT)
    }

    @Test(expected = FormatDateTimeException::class)
    fun `should throw FormatDateTimeException exception with invalid format`() {
        DateTimeConverter.convertStringToLocalDate(DEFAULT_DATE, INVALID_FORMAT)
    }

    @Test(expected = FormatDateTimeException::class)
    fun `should throw the FormatDateTimeException exception with all invalid parameters`() {
        DateTimeConverter.convertStringToLocalDate(INVALID_DATE, INVALID_FORMAT)
    }
}