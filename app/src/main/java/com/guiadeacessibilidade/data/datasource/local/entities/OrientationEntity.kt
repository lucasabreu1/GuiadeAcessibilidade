package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.Orientation

@Entity(tableName = "orientation")
data class OrientationEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "sub_category_id")
    val subCategoryId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "name")
    val shortName: String,

    @ColumnInfo(name = "description1")
    val description1: String,

    @ColumnInfo(name = "description2")
    val description2: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "svg_icon")
    val svgIcon: String
)

fun OrientationEntity.ToOrientation() : Orientation {
    return Orientation(
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