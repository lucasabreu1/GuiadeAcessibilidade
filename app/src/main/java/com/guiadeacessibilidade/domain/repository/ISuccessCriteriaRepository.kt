package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.SuccessCriteria
import kotlinx.coroutines.flow.Flow

interface ISuccessCriteriaRepository {

    suspend fun getAll(): Flow<List<SuccessCriteria>>

    suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): Flow<List<SuccessCriteria>>
    suspend fun loadSuccessCriteriaById(id: String): SuccessCriteria

}