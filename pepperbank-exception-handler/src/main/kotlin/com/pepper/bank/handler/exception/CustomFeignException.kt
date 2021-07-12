package com.pepper.bank.handler.exception

import feign.FeignException

class CustomFeignException(feignException:FeignException, message: String) : Exception(message, feignException) {
}