package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToGuideline
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.ToGuidelineEntity
import com.guiadeacessibilidade.domain.models.Guideline
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GuidelineRepositoryImp(
//    private val localDataSource: IGuidelineRemoteDataSource
    private val remoteDataSource: IGuidelineRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : IGuidelineRepository {
    override suspend fun getAll(): Flow<List<Guideline>> {

        return remoteDataSource.getAll().map { guidelineList ->
            guidelineList.map { networkGuideline ->
                networkGuideline.ToGuidelineEntity().ToGuideline()
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadGuidelineById(id: String): Guideline {

        return withContext(dispatcher){
            remoteDataSource.loadGuidelineById(id).ToGuidelineEntity().ToGuideline()
        }
    }
}