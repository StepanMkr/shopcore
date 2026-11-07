package com.example.shopcore.catalog.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class InvalidPriceRangeException(message: String) : RuntimeException(message)