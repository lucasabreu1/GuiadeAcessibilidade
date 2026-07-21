package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkGuideline
import kotlinx.coroutines.flow.Flow

interface IGuidelineRemoteDataSource {

    fun getAll(): Flow<List<NetworkGuideline>>

    suspend fun loadGuidelineById(id: String): NetworkGuideline
}