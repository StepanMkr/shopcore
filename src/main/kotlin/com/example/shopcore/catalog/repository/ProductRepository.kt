package com.example.shopcore.catalog.repository

import com.example.shopcore.catalog.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProductRepository : JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity>