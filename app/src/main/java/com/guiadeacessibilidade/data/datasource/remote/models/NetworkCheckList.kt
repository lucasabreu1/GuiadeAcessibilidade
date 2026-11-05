package com.guiadeacessibilidade.data.datasource.remote.models

import com.guiadeacessibilidade.domain.models.Checklist

data class NetworkChecklist(
    val id: String,
    val subCategoryId: String,
    val name: String,
    val description: String,
)

fun NetworkChecklist.toChecklist() : Checklist {
    return Checklist(
        this.id,
        this.subCategoryId,
        this.name,
        this.description
    )
}