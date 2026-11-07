package com.example.shopcore.catalog.service

import com.example.shopcore.catalog.dto.CreateProductRequest
import com.example.shopcore.catalog.dto.CreateProductResponse
import com.example.shopcore.catalog.dto.ProductDto
import com.example.shopcore.catalog.entity.ProductEntity
import com.example.shopcore.catalog.exception.InvalidPriceRangeException
import com.example.shopcore.catalog.repository.ProductRepository
import com.example.shopcore.catalog.specification.ProductSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    private val allowedSortFields = setOf("id", "name", "price")

    @Transactional
    fun createProduct(request: CreateProductRequest): CreateProductResponse {
        val newProduct = ProductEntity(
            name = request.name, description = request.description, price = request.price, stock = request.stock
        )

        val savedProduct = productRepository.save(newProduct)

        return CreateProductResponse(
            id = requireNotNull(savedProduct.id),
            name = savedProduct.name,
            description = savedProduct.description,
            price = savedProduct.price,
            stock = savedProduct.stock
        )
    }

    @Transactional(readOnly = true)
    fun getProducts(
        pageable: Pageable, minPrice: BigDecimal?, maxPrice: BigDecimal?
    ): Page<ProductEntity> {

        require(pageable.sort.all { it.property in allowedSortFields }) {
            "Unsupported sorting field"
        }

        if (minPrice != null && minPrice < BigDecimal.ZERO) {
            throw InvalidPriceRangeException("minPrice cannot be negative")
        }

        if (maxPrice != null && maxPrice < BigDecimal.ZERO) {
            throw InvalidPriceRangeException("maxPrice cannot be negative")
        }

        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw InvalidPriceRangeException("minPrice cannot exceed maxPrice")
        }

        val specification = ProductSpecification.byPriceRange(minPrice, maxPrice)

        return productRepository.findAll(specification, pageable)
    }
}