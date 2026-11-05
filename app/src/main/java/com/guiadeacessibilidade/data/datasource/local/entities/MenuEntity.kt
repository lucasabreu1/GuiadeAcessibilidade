package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.Menu

@Entity(tableName = "orientation")
data class MenuEntity(
    @PrimaryKey
    val id : String,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val svgContent: String,

    @ColumnInfo
    val collection: String
)

fun MenuEntity.toMenu(): Menu{

    return Menu(
        this.id,
        this.name,
        this.svgContent,
        this.collection
    )
}
