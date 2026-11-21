package com.example.shopcore.catalog.service

import com.example.shopcore.catalog.dto.CategoryDto
import com.example.shopcore.catalog.dto.CreateCategoryRequest
import com.example.shopcore.catalog.entity.CategoryEntity
import com.example.shopcore.catalog.repository.CategoryRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getCategories(pageable: Pageable): Page<CategoryEntity> {
        return categoryRepository.findAll(pageable)
    }

    fun createCategory(request: CreateCategoryRequest): CategoryDto {
        val category = CategoryEntity(
            name = request.name
        )

        val savedCategory = categoryRepository.save(category)

        return CategoryDto(
            id = requireNotNull(savedCategory.id),
            name = savedCategory.name
        )
    }
}
