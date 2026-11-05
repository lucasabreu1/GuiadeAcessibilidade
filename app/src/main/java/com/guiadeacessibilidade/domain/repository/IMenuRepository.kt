package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.Menu
import com.guiadeacessibilidade.domain.models.OperationResult
import kotlinx.coroutines.flow.Flow

interface IMenuRepository {

    suspend fun getAll(): Flow<OperationResult<List<Menu>>>
}