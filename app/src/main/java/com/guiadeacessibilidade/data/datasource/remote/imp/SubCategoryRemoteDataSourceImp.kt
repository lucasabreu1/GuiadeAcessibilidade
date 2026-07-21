package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SubCategoryRemoteDataSourceImp(
    private val subCategoryApi: ISubCategoryApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 3600000
) : ISubCategoryRemoteDataSource {

    override fun getAll(): Flow<List<NetworkSubCategory>> {

        return flow {
            while (true){
                val subCategoryList = subCategoryApi.getAll()
                emit(subCategoryList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override fun getSubCategoriesByCategoryId(categoryId: String): Flow<List<NetworkSubCategory>> {

        return flow {
            while (true){
                val subCategoryList = subCategoryApi.getSubCategoriesByCategoryId(categoryId)
                emit(subCategoryList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadSubCategoryById(id: String): NetworkSubCategory {

        return withContext(dispatcher){
            subCategoryApi.loadSubCategoryById(id)
        }

    }
}