package com.example.shopcore.catalog.controller

import com.example.shopcore.catalog.dto.CreateProductRequest
import com.example.shopcore.catalog.dto.CreateProductResponse
import com.example.shopcore.catalog.dto.ProductDto
import com.example.shopcore.catalog.mapper.toDto
import com.example.shopcore.catalog.service.ProductService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun getProducts(
        @PageableDefault(size = 10, page = 0, sort = ["id"]) pageable: Pageable,
        @RequestParam(required = false) minPrice: BigDecimal?,
        @RequestParam(required = false) maxPrice: BigDecimal?
    ): ResponseEntity<Page<ProductDto>> {
        val productsEntity = productService.getProducts(pageable, minPrice, maxPrice)
        val productsDto = productsEntity.map { entity -> entity.toDto() }

        return ResponseEntity.ok(productsDto)
    }

    @PostMapping
    fun createProduct(
        @Valid @RequestBody request: CreateProductRequest
    ): ResponseEntity<CreateProductResponse> {
        val product = productService.createProduct(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(product)
    }
}