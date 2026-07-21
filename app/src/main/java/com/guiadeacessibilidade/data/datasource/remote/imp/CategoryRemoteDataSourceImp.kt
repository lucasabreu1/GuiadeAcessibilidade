package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CategoryRemoteDataSourceImp(
    private val categoryApi: ICategoryApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 3600000
) : ICategoryRemoteDataSource {

    override fun getAll(): Flow<List<NetworkCategory>> {

        return flow {
            while (true){
                val categoryList = categoryApi.getAll()
                emit(categoryList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadCategoryById(id: String): NetworkCategory {

        return withContext(dispatcher){
            categoryApi.loadCategoryById(id)
        }

    }
}