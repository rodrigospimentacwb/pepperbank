package com.pepperbank.utils.converters

import org.json.JSONObject
import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class JsonConverterTest {

    class CustomerTest(
        var name:String,
        var birthdate: LocalDate,
        var createAt: LocalDateTime,
        var timeAt: LocalTime,
        var price: Double)

    var formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")

    private fun generateLocalTime(time:String) = LocalTime.parse(time)

    private fun generateLocalDateTime(dateTime:String):LocalDateTime = LocalDateTime.parse(dateTime, formatter)

    private fun generateCustomerTest():CustomerTest =
        CustomerTest("Foo Foo",
            LocalDate.now(),
            generateLocalDateTime("2021-05-10 19:25:00:720"),
            generateLocalTime("12:20:00"),
            1.52)

    @Test
    fun `should to json`(){

        val customerTest = generateCustomerTest()
        val jsonCustomer = JsonConverter.toJson(generateCustomerTest())
        val jsonObject = JSONObject(jsonCustomer)
        Assert.assertEquals(customerTest.name,jsonObject.get("name"))
        Assert.assertEquals(customerTest.birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE),jsonObject.get("birthdate"))
        Assert.assertEquals(customerTest.createAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),jsonObject.get("createAt"))
        Assert.assertEquals(customerTest.timeAt.format(DateTimeFormatter.ISO_LOCAL_TIME),jsonObject.get("timeAt"))
        Assert.assertEquals(customerTest.price,jsonObject.get("price"))
    }

    @Test
    fun `should to json list`(){

    }

    @Test
    fun `should from json`(){

    }

    @Test
    fun `should from json list`(){

    }

    @Test
    fun `should convert`(){

    }

    @Test
    fun `should convert list`(){

    }

    @Test
    fun `should copy`(){

    }
}