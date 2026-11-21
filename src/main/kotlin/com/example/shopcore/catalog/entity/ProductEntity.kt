package com.example.shopcore.catalog.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class ProductEntity(
    @Column(nullable = false)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String?,

    @Column(nullable = false, precision = 12, scale = 2)
    var price: BigDecimal,

    @Column(nullable = false)
    var stock: Int,

    @Column(name = "category_id")
    var categoryId: Long? = null,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}