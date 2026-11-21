package com.example.shopcore.order.entity

import com.example.shopcore.order.model.OrderStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "orders")
class OrderEntity(
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var status: OrderStatus = OrderStatus.NEW,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}
