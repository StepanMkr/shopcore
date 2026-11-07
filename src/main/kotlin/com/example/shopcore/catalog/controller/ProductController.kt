package com.example.shopcore.catalog.controller

import com.example.shopcore.catalog.dto.CreateProductRequest
import com.example.shopcore.catalog.dto.CreateProductResponse
import com.example.shopcore.catalog.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: CreateProductRequest
    ): ResponseEntity<CreateProductResponse> {
        val product = productService.createProduct(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }
}