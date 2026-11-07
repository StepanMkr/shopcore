package com.example.shopcore.catalog.mapper

import com.example.shopcore.catalog.dto.ProductDto
import com.example.shopcore.catalog.entity.ProductEntity

fun ProductEntity.toDto() = ProductDto(
    id = requireNotNull(id), name = name, description = description, price = price, stock = stock
)