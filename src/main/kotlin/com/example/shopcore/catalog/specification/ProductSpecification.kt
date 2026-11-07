package com.example.shopcore.catalog.specification

import com.example.shopcore.catalog.entity.ProductEntity
import org.springframework.data.jpa.domain.Specification
import java.math.BigDecimal
import jakarta.persistence.criteria.Predicate

object ProductSpecification {
    fun byPriceRange(
        minPrice: BigDecimal?,
        maxPrice: BigDecimal?
    ): Specification<ProductEntity> {
        return Specification { root, _, cb ->
            val predicates = mutableListOf<Predicate>()
            minPrice?.let {
                predicates += cb.greaterThanOrEqualTo(
                    root.get<BigDecimal>("price"),
                    it
                )
            }

            maxPrice?.let {
                predicates += cb.lessThanOrEqualTo(
                    root.get<BigDecimal>("price"),
                    it
                )
            }

            cb.and(*predicates.toTypedArray())
        }
    }
}