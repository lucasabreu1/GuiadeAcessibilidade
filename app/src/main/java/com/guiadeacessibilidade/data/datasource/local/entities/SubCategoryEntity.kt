package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.SubCategory

@Entity(tableName = "sub_category")
data class SubCategoryEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "category_id")
    val categoryId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "svg_content")
    val svgContent: String
)

fun SubCategoryEntity.ToSubCategory() : SubCategory {
    return SubCategory(
        this.id,
        this.categoryId,
        this.name,
        this.svgContent
    )
}