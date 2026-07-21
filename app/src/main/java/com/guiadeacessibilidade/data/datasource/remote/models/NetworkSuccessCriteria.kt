package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

data class NetworkSuccessCriteria(
    val id: String,
    val guidelineId: String,
    val principleId: String,
    val name: String,
    val level: String,
    val link: String,
    val description: String,
    val mainTag: String,
    val searchTags: List<String>
)

 fun NetworkSuccessCriteria.toSuccessCriteriaEntity() : SuccessCriteriaEntity {

    return SuccessCriteriaEntity(
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