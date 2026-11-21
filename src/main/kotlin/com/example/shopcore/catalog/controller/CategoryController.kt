package com.example.shopcore.catalog.controller

import com.example.shopcore.catalog.dto.CategoryDto
import com.example.shopcore.catalog.dto.CreateCategoryRequest
import com.example.shopcore.catalog.service.CategoryService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/categories")
class CategoryController(
    private val categoryService: CategoryService
) {
    @GetMapping
    fun getCategories(@PageableDefault(size = 10, sort = ["id"]) pageable: Pageable): ResponseEntity<Page<CategoryDto>> {
        val categories = categoryService.getCategories(pageable)

        val result = categories.map { category ->
            CategoryDto(
                id = requireNotNull(category.id),
                name = category.name
            )
        }

        return ResponseEntity.ok(result)
    }

    @PostMapping
    fun createCategory(@Valid @RequestBody request: CreateCategoryRequest): ResponseEntity<CategoryDto> {
        val category = categoryService.createCategory(request)

        return ResponseEntity.status(HttpStatus.CREATED).body(category)
    }
}
