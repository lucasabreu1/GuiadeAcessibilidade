package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.GuidelineEntity

data class NetworkGuideline(
    val id: String,
    val briefPurpose: String,
    val name: String,
    val number: String,
    val principleId: String
)

fun NetworkGuideline.ToGuidelineEntity() : GuidelineEntity {

    return GuidelineEntity(
        this.id,
        this.briefPurpose,
        this.name,
        this.number,
        this.principleId
    )
}