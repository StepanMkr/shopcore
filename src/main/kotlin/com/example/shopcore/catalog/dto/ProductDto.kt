package com.example.shopcore.catalog.dto

import java.math.BigDecimal

data class ProductDto(
    val id: Long,
    val name: String,
    var description: String?,
    var price: BigDecimal,
    var stock: Int,
    val categoryId: Long
)