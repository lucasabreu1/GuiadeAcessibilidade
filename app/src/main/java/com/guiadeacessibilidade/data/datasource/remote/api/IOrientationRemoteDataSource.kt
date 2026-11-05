package com.guiadeacessibilidade.data.datasource.remote.api
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation
import kotlinx.coroutines.flow.Flow

interface IOrientationRemoteDataSource {

    fun getAll(): Flow<List<NetworkOrientation>>

    fun getOrientationsBySubCategoryId(subCategoryId: String): Flow<List<NetworkOrientation>>

    suspend fun loadOrientationById(id: String): NetworkOrientation?

}