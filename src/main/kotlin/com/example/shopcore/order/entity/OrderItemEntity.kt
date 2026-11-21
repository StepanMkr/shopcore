package com.example.shopcore.order.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "order_items",
    uniqueConstraints = [UniqueConstraint(
        name = "uq_order_items_order_product",
        columnNames = ["order_id", "product_id"]
    )]
)
class OrderItemEntity(
    @Column(name = "order_id", nullable = false)
    var orderId: Long,

    @Column(name = "product_id", nullable = false)
    var productId: Long,

    @Column(nullable = false)
    var quantity: Int,

    @Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
    var unitPrice: BigDecimal
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
