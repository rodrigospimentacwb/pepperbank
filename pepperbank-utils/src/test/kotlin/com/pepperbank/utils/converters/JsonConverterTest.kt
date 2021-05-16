package com.pepperbank.utils.converters

import org.json.JSONArray
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
        var price: Double,
        var nullValue: String?,
        var emptyValue: String){
        constructor() : this("", LocalDate.now(), LocalDateTime.now(), LocalTime.now(),0.0, null, "")
    }

    class CustomerTestTO(
        var name:String,
        var birthdate: LocalDate,
        var createAt: LocalDateTime,
        var timeAt: LocalTime,
        var price: Double,
        var nullValue: String?,
        var emptyValue: String){
        constructor() : this("", LocalDate.now(), LocalDateTime.now(), LocalTime.now(),0.0, null, "")
    }

    var formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS")

    private fun generateLocalTime(time:String) = LocalTime.parse(time)

    private fun generateLocalDateTime(dateTime:String):LocalDateTime = LocalDateTime.parse(dateTime, formatter)

    private fun generateCustomerTest():CustomerTest =
        CustomerTest("Foo Foo",
            LocalDate.now(),
            generateLocalDateTime("2021-05-10 19:25:00:720"),
            generateLocalTime("12:20:00"),
            1.52,
        null,
        "")

    private fun generateListCustomers(): List<CustomerTest> {
        val customerTest = generateCustomerTest()
        var customers:MutableList<CustomerTest> = mutableListOf()
        customers.add(customerTest)
        customers.add(customerTest)
        customers.add(customerTest)
        return customers
    }

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
        Assert.assertEquals(customerTest.nullValue,null)
        Assert.assertEquals(customerTest.emptyValue,"")
    }

    @Test
    fun `should to json list`(){
        val customerTest = generateCustomerTest()
        val jsonCustomers = JsonConverter.toJson(generateListCustomers())
        val jsonArray = JSONArray(jsonCustomers)
        Assert.assertEquals(customerTest.name,JSONObject(jsonArray.get(2).toString()).get("name"))
        Assert.assertEquals(customerTest.birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE),JSONObject(jsonArray.get(1).toString()).get("birthdate"))
        Assert.assertEquals(customerTest.createAt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),JSONObject(jsonArray.get(0).toString()).get("createAt"))
        Assert.assertEquals(customerTest.timeAt.format(DateTimeFormatter.ISO_LOCAL_TIME),JSONObject(jsonArray.get(0).toString()).get("timeAt"))
        Assert.assertEquals(customerTest.price,JSONObject(jsonArray.get(2).toString()).get("price"))
        Assert.assertEquals(JSONObject.NULL,JSONObject(jsonArray.get(2).toString()).get("nullValue"))
        Assert.assertEquals(customerTest.emptyValue,JSONObject(jsonArray.get(2).toString()).get("emptyValue"))
    }

    @Test
    fun `should from json`(){
        val customerTest = generateCustomerTest()
        val jsonCustomer = JsonConverter.toJson(generateCustomerTest())
        var customer = JsonConverter.fromJson(jsonCustomer, CustomerTest::class.java)
        Assert.assertEquals(customerTest.name,customer.name)
        Assert.assertEquals(customerTest.birthdate,customer.birthdate)
        Assert.assertEquals(customerTest.createAt,customer.createAt)
        Assert.assertEquals(customerTest.timeAt,customer.timeAt)
        Assert.assertEquals(customerTest.price,customer.price,0.0)
        Assert.assertEquals(customerTest.nullValue,customer.nullValue)
        Assert.assertEquals(customerTest.emptyValue,customer.emptyValue)
    }

    @Test
    fun `should convert`(){
        val customerTest = generateCustomerTest()
        var customerTO = JsonConverter.convert(customerTest,CustomerTestTO::class.java)
        Assert.assertEquals(customerTest.name,customerTO.name)
        Assert.assertEquals(customerTest.birthdate,customerTO.birthdate)
        Assert.assertEquals(customerTest.createAt,customerTO.createAt)
        Assert.assertEquals(customerTest.timeAt,customerTO.timeAt)
        Assert.assertEquals(customerTest.price,customerTO.price,0.0)
        Assert.assertEquals(customerTest.nullValue,customerTO.nullValue)
        Assert.assertEquals(customerTest.emptyValue,customerTO.emptyValue)
    }

    @Test
    fun `should convert list`(){
        var customers = generateListCustomers()
        var customersTO = JsonConverter.convert(customers,CustomerTestTO::class.java)
        Assert.assertEquals(customers[0].name,customersTO[0].name)
        Assert.assertEquals(customers[1].birthdate,customersTO[1].birthdate)
        Assert.assertEquals(customers[2].createAt,customersTO[2].createAt)
        Assert.assertEquals(customers[1].timeAt,customersTO[1].timeAt)
        Assert.assertEquals(customers[0].price,customersTO[0].price,0.0)
        Assert.assertEquals(customers[1].nullValue,customersTO[1].nullValue)
        Assert.assertEquals(customers[2].emptyValue,customersTO[1].emptyValue)
    }

    @Test
    fun `should copy`(){
        var customer = generateCustomerTest()
        var customerCopy = JsonConverter.copy(customer)
        Assert.assertEquals(customer.name,customerCopy.name)
        Assert.assertEquals(customer.birthdate,customerCopy.birthdate)
        Assert.assertEquals(customer.createAt,customerCopy.createAt)
        Assert.assertEquals(customer.timeAt,customerCopy.timeAt)
        Assert.assertEquals(customer.price,customerCopy.price,0.0)
        Assert.assertEquals(customer.nullValue,customerCopy.nullValue)
        Assert.assertEquals(customer.emptyValue,customerCopy.emptyValue)
    }
}