package com.example.shopcore.order.service

import com.example.shopcore.catalog.repository.ProductRepository
import com.example.shopcore.order.repository.OrderRepository
import com.example.shopcore.order.repository.OrderItemRepository
import com.example.shopcore.order.dto.CreateOrderItemRequest
import com.example.shopcore.order.dto.CreateOrderRequest
import com.example.shopcore.order.dto.OrderDto
import com.example.shopcore.order.dto.OrderItemDto
import com.example.shopcore.order.entity.OrderEntity
import com.example.shopcore.order.entity.OrderItemEntity
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.math.BigDecimal

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
    private val productRepository: ProductRepository
) {
    @Transactional
    fun createOrder(request: CreateOrderRequest): OrderDto {
        if (request.items.isEmpty() || request.items.any { it.productId <= 0 || it.quantity <= 0 }) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Заказ должен содержать товары с положительными ID и количеством")
        }

        val mergedItems: List<CreateOrderItemRequest> = request.items
            .groupingBy { it.productId }
            .reduce { _, acc, item ->
                val quantity = try {
                    Math.addExact(acc.quantity, item.quantity)
                } catch (exception: ArithmeticException) {
                    throw ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Слишком большое количество товара ${item.productId}"
                    )
                }
                acc.copy(quantity = quantity)
            }
            .values
            .toList()

        val productIds = mergedItems.map { it.productId }
        val products = productRepository.findAllByIdForUpdate(productIds)
        val productsById = products.associateBy { requireNotNull(it.id) }

        for (item in mergedItems) {
            val product = productsById[item.productId]
                ?: throw ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Товар ${item.productId} не найден"
                )

            if (product.stock < item.quantity) {
                throw ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Недостаточно товара ${item.productId}: доступно ${product.stock}, запрошено ${item.quantity}"
                )
            }
        }
        val order = orderRepository.save(OrderEntity())
        val orderId = requireNotNull(order.id)
        val orderItems = mergedItems.map { item ->
            val product = productsById.getValue(item.productId)

            product.stock -= item.quantity

            OrderItemEntity(
                orderId = orderId,
                productId = item.productId,
                quantity = item.quantity,
                unitPrice = product.price
            )
        }

        val savedItems = orderItemRepository.saveAll(orderItems)

        return OrderDto(
            id = orderId,
            status = order.status,
            createdAt = order.createdAt,
            items = savedItems.map { item ->
                OrderItemDto(
                    id = requireNotNull(item.id),
                    productId = item.productId,
                    quantity = item.quantity,
                    unitPrice = item.unitPrice
                )
            },
            totalAmount = savedItems.fold(BigDecimal.ZERO) { total, item ->
                total + item.unitPrice.multiply(item.quantity.toBigDecimal())
            }
        )
    }
}
