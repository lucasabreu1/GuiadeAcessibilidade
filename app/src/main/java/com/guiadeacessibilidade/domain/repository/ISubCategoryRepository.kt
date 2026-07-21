package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import kotlinx.coroutines.flow.Flow

interface ISubCategoryRepository {

    suspend fun getAll(): Flow<OperationResult<List<SubCategory>>>

    suspend fun getSubCategoriesByCategoryId(categoryId: String): Flow<OperationResult<List<SubCategory>>>
    suspend fun loadSubCategoryById(id: String): SubCategory

}