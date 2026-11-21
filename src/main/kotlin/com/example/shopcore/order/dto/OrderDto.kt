package com.example.shopcore.order.dto

import com.example.shopcore.order.model.OrderStatus
import java.math.BigDecimal
import java.time.Instant

data class OrderDto(
    val id: Long,
    val status: OrderStatus,
    val createdAt: Instant,
    val items: List<OrderItemDto>,
    val totalAmount: BigDecimal
)

data class OrderItemDto(
    val id: Long,
    val productId: Long,
    val quantity: Int,
    val unitPrice: BigDecimal
)
