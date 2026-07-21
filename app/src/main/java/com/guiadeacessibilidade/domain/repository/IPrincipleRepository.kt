package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.Principle

interface IPrincipleRepository {

    suspend fun loadPrincipleById(id: String): Principle
}