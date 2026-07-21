package com.guiadeacessibilidade.ui

import kotlinx.serialization.Serializable

@Serializable
object CategoriesRoute

@Serializable
data class SubCategoriesRoute(
    val categoryId: String,
    val categoryName: String
)