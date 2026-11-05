package com.guiadeacessibilidade.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Orientation(
    val id: String,
    val subCategoryId: String,
    val name: String,
    val shortName: String,
    val description1: String,
    val description2: String,
    val imageUrl: String,
    val svgIcon: String
)

