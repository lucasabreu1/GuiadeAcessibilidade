package com.guiadeacessibilidade.domain.models

data class CompleteSuccessCriteria(
    val sucessCriteria: SuccessCriteria,
    val guideline: Guideline,
    val principle: Principle
)