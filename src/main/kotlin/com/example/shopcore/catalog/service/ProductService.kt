package com.example.shopcore.catalog.service

import com.example.shopcore.catalog.dto.CreateProductRequest
import com.example.shopcore.catalog.dto.CreateProductResponse
import com.example.shopcore.catalog.entity.ProductEntity
import com.example.shopcore.catalog.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    @Transactional
    fun createProduct(request: CreateProductRequest): CreateProductResponse {
        val newProduct = ProductEntity(
            name = request.name,
            description = request.description,
            price = request.price,
            stock = request.stock
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
}