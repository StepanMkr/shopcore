package com.example.shopcore.catalog.repository

import com.example.shopcore.catalog.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<CategoryEntity, Long>