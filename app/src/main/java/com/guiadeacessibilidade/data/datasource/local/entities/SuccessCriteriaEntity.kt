package com.guiadeacessibilidade.data.datasource.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guiadeacessibilidade.domain.models.SuccessCriteria

@Entity(tableName = "success_criteria")
data class SuccessCriteriaEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "guideline_id")
    val guidelineId: String,

    @ColumnInfo(name = "principle_id")
    val principleId: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("level")
    val level: String,

    @ColumnInfo(name= "link")
    val link: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "main_tag")
    val mainTag: String,

    @ColumnInfo(name = "search_tags")
    val searchTags: List<String>


)

fun SuccessCriteriaEntity.ToSuccessCriteria() : SuccessCriteria {

    return SuccessCriteria(
        this.id,
        this.guidelineId,
        this.principleId,
        this.name,
        this.level,
        this.link,
        this.description,
        this.mainTag,
        this.searchTags
    )
}
