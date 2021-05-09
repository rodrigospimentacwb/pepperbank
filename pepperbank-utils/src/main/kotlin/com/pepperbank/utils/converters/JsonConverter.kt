package com.pepperbank.utils.converters

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.IOException


class JsonConverter {

    companion object{

        private fun getMapper(): ObjectMapper {
            val mapper = ObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            mapper.registerModule(JavaTimeModule())
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            return mapper
        }

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
            return convert(source, source as Class<O>)
        }
    }
}