package com.guiadeacessibilidade.domain.models

data class Guideline(
    val id: String,
    val briefPurpose: String,
    val name: String,
    val number: String,
    val principleId: String
)
