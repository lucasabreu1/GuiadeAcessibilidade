package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineApi
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkGuideline
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GuidelineRemoteDataSourceImp(
    private val guidelineApi: IGuidelineApi,
    private val dispatcher: CoroutineDispatcher,
) : IGuidelineRemoteDataSource{
    override fun getAll(): Flow<List<NetworkGuideline>> {

        return flow {

            val guidelineList = guidelineApi.getAll()
            emit(guidelineList)

        }.flowOn(dispatcher)
    }

    override suspend fun loadGuidelineById(id: String): NetworkGuideline {

        val guideline = guidelineApi.loadGuidelineById(id)
        return guideline
    }

}