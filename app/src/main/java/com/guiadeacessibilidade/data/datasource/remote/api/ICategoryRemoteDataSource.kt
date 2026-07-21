package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkCategory
import kotlinx.coroutines.flow.Flow

interface ICategoryRemoteDataSource {

    fun getAll(): Flow<List<NetworkCategory>>

    suspend fun loadCategoryById(id: String): NetworkCategory
}