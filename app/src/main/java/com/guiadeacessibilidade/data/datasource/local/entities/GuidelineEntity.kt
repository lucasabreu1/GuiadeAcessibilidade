package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.Guideline

@Entity(tableName = "guideline")
data class GuidelineEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "brief_purpose")
    val briefPurpose: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "number")
    val number: String,

    @ColumnInfo(name = "principle_id")
    val principleId: String
)

fun GuidelineEntity.ToGuideline() : Guideline {
    return Guideline(
        this.id,
        this.briefPurpose,
        this.name,
        this.number,
        this.principleId
    )
}
