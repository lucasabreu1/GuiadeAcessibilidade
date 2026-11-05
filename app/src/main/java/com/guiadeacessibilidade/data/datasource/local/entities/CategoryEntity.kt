package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.Category

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "svg_content")
    val svgContent: String,
)

fun CategoryEntity.ToCategory() : Category {
    return Category(
        this.id,
        this.name,
        this.svgContent
    )
}