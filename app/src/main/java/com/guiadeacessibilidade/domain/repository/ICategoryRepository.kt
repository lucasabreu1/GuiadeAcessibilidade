package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface ICategoryRepository {

    suspend fun getAll(): Flow<List<Category>>

    suspend fun loadCategoryById(id: String): Category


}