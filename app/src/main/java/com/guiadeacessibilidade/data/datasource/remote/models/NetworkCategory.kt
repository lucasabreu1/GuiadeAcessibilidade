package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.CategoryEntity

data class NetworkCategory(
    val id: String,
    val name: String,
    val svgContent: String
)

fun NetworkCategory.ToCategoryEntity() : CategoryEntity {
    return CategoryEntity(
        this.id,
        this.name,
        svgContent
    )
}