package com.example.shopcore.catalog.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateCategoryRequest(
    @field:NotBlank
    @field:Size(max = 255)
    var name: String
)
