package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationApi
import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class OrientationRemoteDataSourceImp(
    private val orientationApi: IOrientationApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 3600000
) : IOrientationRemoteDataSource {

    override fun getAll(): Flow<List<NetworkOrientation>> {

        return flow {
            while (true){
                val orientationList = orientationApi.getAll()
                emit(orientationList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override fun getOrientationsBySubCategoryId(subCategoryId: String): Flow<List<NetworkOrientation>> {

        return flow {
            while (true){
                val orientationList = orientationApi.getOrientationsBySubCategoryId(subCategoryId)
                emit(orientationList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadOrientationById(id: String): NetworkOrientation? {

        return withContext(dispatcher){
            orientationApi.loadOrientationById(id)
        }

    }
}