package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.data.datasource.local.entities.MenuEntity

data class NetworkMenu(
    val id: String,
    val name: String,
    val svgContent: String,
    val collection: String
)

fun NetworkMenu.ToMenuEntity() : MenuEntity{
    return MenuEntity(
        this.id,
        this.name,
        this.svgContent,
        this.collection
    )
}
