package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.CategoryEntity

interface ICategoryDAOContract {

    suspend fun getAll(): List<CategoryEntity>

    suspend fun loadCategoryById(id: String): CategoryEntity
}