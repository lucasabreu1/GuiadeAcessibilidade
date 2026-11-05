package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkMenu
import kotlinx.coroutines.flow.Flow

interface IMenuRemoteDataSource {

    suspend fun getAll(): Flow<List<NetworkMenu>>
}