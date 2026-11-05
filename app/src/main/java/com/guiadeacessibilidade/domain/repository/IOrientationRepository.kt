package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.models.SubCategory
import kotlinx.coroutines.flow.Flow

interface IOrientationRepository {

    suspend fun getAll(): Flow<OperationResult<List<Orientation>>>

    suspend fun getOrientationsBySubCategoryId(subCategoryId: String): Flow<OperationResult<List<Orientation>>>
    suspend fun loadOrientationById(id: String): OperationResult<Orientation>
}
