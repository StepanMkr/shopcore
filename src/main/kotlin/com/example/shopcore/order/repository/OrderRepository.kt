package com.example.shopcore.order.repository

import com.example.shopcore.order.entity.OrderEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<OrderEntity, Long>
