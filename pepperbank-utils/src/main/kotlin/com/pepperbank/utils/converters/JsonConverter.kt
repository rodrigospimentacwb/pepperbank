package com.pepperbank.utils.converters

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.pepperbank.utils.serializers.LocalDateDeserializer
import com.pepperbank.utils.serializers.LocalDateSerializer
import com.pepperbank.utils.serializers.LocalDateTimeDeserializer
import com.pepperbank.utils.serializers.LocalDateTimeSerializer
import com.pepperbank.utils.serializers.LocalTimeDeserializer
import com.pepperbank.utils.serializers.LocalTimeSerializer
import java.io.IOException
import java.time.LocalDate

import java.time.LocalDateTime
import java.time.LocalTime


class JsonConverter {

    companion object{

        private fun getMapper(): ObjectMapper {
            val mapper = ObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            mapper.registerModule(gerenateJavaTimeModule())
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper
        }

        private fun gerenateJavaTimeModule(): JavaTimeModule {
            val javaTimeModule = JavaTimeModule()
            addLocalDateSerialize(javaTimeModule)
            addLocalDateDeserialize(javaTimeModule)
            addLocalDateTimeSerializer(javaTimeModule)
            addLocalDateTimeDeserializer(javaTimeModule)
            addLocalTimeSerializer(javaTimeModule)
            addLocalTimeDeserializer(javaTimeModule)
            return javaTimeModule
        }

        private fun addLocalDateSerialize(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addSerializer(LocalDate::class.java, LocalDateSerializer())

        private fun addLocalDateDeserialize(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addDeserializer(LocalDate::class.java, LocalDateDeserializer())

        private fun addLocalDateTimeSerializer(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer())

        private fun addLocalDateTimeDeserializer(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer())

        private fun addLocalTimeSerializer(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addSerializer(LocalTime::class.java, LocalTimeSerializer())

        private fun addLocalTimeDeserializer(javaTimeModule:JavaTimeModule) =
            javaTimeModule.addDeserializer(LocalTime::class.java, LocalTimeDeserializer())


        @Throws(JsonProcessingException::class)
        fun <T> toJson(value: T): String {
            return getMapper().writeValueAsString(value)
        }

        @Throws(JsonProcessingException::class)
        fun <T> toJson(value: List<T>): String {
            return getMapper().writeValueAsString(value)
        }

        @Throws(IOException::class)
        fun <T> fromJson(json: String, clazz: Class<T>): T {
            return getMapper().readValue(json, clazz)
        }

        @Throws(IOException::class)
        fun <T, O> convert(source: O, clazz: Class<T>): T {
            return getMapper().readValue(toJson(source), clazz)
        }

        @Throws(IOException::class)
        fun <T, O> convert(source: List<O>, clazz: Class<T>): List<T> {
            return getMapper().readValue(toJson(source), getMapper().typeFactory.constructCollectionType(
                MutableList::class.java,
                clazz
            ))
        }

        @Throws(IOException::class)
        fun <O> copy(source: O): O {
            return convert(source, source!!::class.java)
        }
    }
}