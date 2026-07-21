package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaApi
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class SuccessCriteriaRemoteDataSourceImp(
    private val sucessCriteriaApi: ISuccessCriteriaApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 3600000
) : ISuccessCriteriaRemoteDataSource {

    override fun getAll(): Flow<List<NetworkSuccessCriteria>> {

        return flow {
            while (true){
                val successCriteriaList = sucessCriteriaApi.getAll()
                emit(successCriteriaList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria {

        return withContext(dispatcher){
            sucessCriteriaApi.loadSuccessCriteriaById(id)
        }

    }
}