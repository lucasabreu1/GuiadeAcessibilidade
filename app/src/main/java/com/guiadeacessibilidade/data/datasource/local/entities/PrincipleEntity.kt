package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.Principle

@Entity(tableName = "principle")
data class PrincipleEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "number")
    val number: String
)

fun PrincipleEntity.ToPrinciple() : Principle {
    return Principle(
        this.id,
        this.name,
        this.number
    )
}
