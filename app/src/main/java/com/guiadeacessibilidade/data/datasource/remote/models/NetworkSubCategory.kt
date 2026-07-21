package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.SubCategoryEntity

data class NetworkSubCategory(
    val id: String,
    val categoryId: String,
    val name: String,
    val svgContent: String
)

fun NetworkSubCategory.ToSubCategoryEntity() : SubCategoryEntity {
    return SubCategoryEntity(
        this.id,
        this.categoryId,
        this.name,
        this.svgContent
    )
}