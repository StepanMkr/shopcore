package com.example.shopcore.order.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Positive

data class CreateOrderRequest(
    @field:NotEmpty
    @field:Valid
    val items: List<CreateOrderItemRequest>
)

data class CreateOrderItemRequest(
    @field:Positive
    val productId: Long,

    @field:Positive
    val quantity: Int
)
