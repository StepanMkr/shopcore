package com.example.shopcore.catalog.dto

import java.math.BigDecimal

data class CreateProductResponse(
    val id: Long,
    val name: String,
    var description: String?,
    var price: BigDecimal,
    var stock: Int
)