package com.guiadeacessibilidade.domain.models

data class SuccessCriteria(
    val id: String,
    val guidelineId: String,
    val principleId: String,
    val name: String,
    val level: String,
    val link: String,
    val description: String,
    val mainTag: String,
    val searchTags: List<String>,
    val subCategoryIdList: List<String>
)
