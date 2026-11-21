package com.example.shopcore.catalog.service

import com.example.shopcore.catalog.dto.CreateProductRequest
import com.example.shopcore.catalog.dto.CreateProductResponse
import com.example.shopcore.catalog.entity.ProductEntity
import com.example.shopcore.catalog.exception.InvalidPriceRangeException
import com.example.shopcore.catalog.repository.CategoryRepository
import com.example.shopcore.catalog.repository.ProductRepository
import com.example.shopcore.catalog.specification.ProductSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {
    private val allowedSortFields = setOf("id", "name", "price")

    @Transactional
    fun createProduct(request: CreateProductRequest): CreateProductResponse {
        if (!categoryRepository.existsById(request.categoryId)) {
            throw ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Категория с id=${request.categoryId} не найдена"
            )
        }

        val newProduct = ProductEntity(
            name = request.name,
            description = request.description,
            price = request.price,
            stock = request.stock,
            categoryId = request.categoryId
        )

        val savedProduct = productRepository.save(newProduct)

        return CreateProductResponse(
            id = requireNotNull(savedProduct.id),
            name = savedProduct.name,
            description = savedProduct.description,
            price = savedProduct.price,
            stock = savedProduct.stock,
            categoryId = savedProduct.categoryId
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