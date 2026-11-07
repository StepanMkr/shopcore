package com.example.shopcore.catalog.repository

import com.example.shopcore.catalog.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<ProductEntity, Long>