package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.OrientationEntity

data class NetworkOrientation(
    val id: String,
    val subCategoryId: String,
    val name: String,
    val shortName: String,
    val description1: String,
    val description2: String,
    val imageUrl: String,
    val svgIcon: String
)

fun NetworkOrientation.ToOrientationEntity() : OrientationEntity {
    return OrientationEntity(
        this.id,
        this.subCategoryId,
        this.name,
        this.shortName,
        this.description1,
        this.description2,
        this.imageUrl,
        this.svgIcon
    )
}