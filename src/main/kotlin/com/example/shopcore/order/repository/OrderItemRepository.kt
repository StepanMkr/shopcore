package com.example.shopcore.order.repository

import com.example.shopcore.order.entity.OrderItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderItemRepository : JpaRepository<OrderItemEntity, Long>
