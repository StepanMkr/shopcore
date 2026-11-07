package com.example.shopcore.catalog.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class CreateProductRequest(
    @field:NotBlank
    @field:Size(max = 255)
    val name: String,

    val description: String?,

    @field:DecimalMin("0.01")
    val price: BigDecimal,

    @field:Min(0)
    val stock: Int
)