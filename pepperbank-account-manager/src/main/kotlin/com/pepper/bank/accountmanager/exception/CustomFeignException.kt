package com.pepper.bank.accountmanager.exception

import feign.FeignException

class CustomFeignException(feignException:FeignException, message: String) : Exception(message, feignException) {
}