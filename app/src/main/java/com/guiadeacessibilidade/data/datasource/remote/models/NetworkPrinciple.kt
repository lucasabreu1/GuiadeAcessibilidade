package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.PrincipleEntity
import com.guiadeacessibilidade.domain.models.Principle

data class NetworkPrinciple(
    val id: String,
    val name: String,
    val number: String
)

fun NetworkPrinciple.ToPrincipleEntity() : PrincipleEntity {

    return PrincipleEntity(
        this.id,
        this.name,
        this.number
    )
}