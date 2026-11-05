package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToSuccessCriteria
import com.guiadeacessibilidade.data.datasource.remote.api.ISuccessCriteriaRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.toSuccessCriteriaEntity
import com.guiadeacessibilidade.domain.models.SuccessCriteria
import com.guiadeacessibilidade.domain.repository.ISuccessCriteriaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SuccessCriteriaRepositoryImp (
    //private val localDataSource: ISuccessCriteriaLocalDataSource,
    private val remoteDataSource: ISuccessCriteriaRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : ISuccessCriteriaRepository {

    override suspend fun getAll(): Flow<List<SuccessCriteria>> {

        return remoteDataSource.getAll().map { successCriteriaList ->
            successCriteriaList.map { networkSuccessCriteria ->
                networkSuccessCriteria.toSuccessCriteriaEntity().ToSuccessCriteria()
            }
        }.flowOn(dispatcher)
    }

    override suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): Flow<List<SuccessCriteria>>{
        return remoteDataSource.getSuccessCriteriasBySubCategoryId(subCategoryId).map {
            successCriteriaList ->
                successCriteriaList.map { networkSuccessCriteria ->
                    networkSuccessCriteria.toSuccessCriteriaEntity().ToSuccessCriteria()
                }
        }.flowOn(dispatcher)
    }

    override suspend fun loadSuccessCriteriaById(id: String): SuccessCriteria {

        return withContext(dispatcher){
            remoteDataSource.loadSuccessCriteriaById(id).toSuccessCriteriaEntity().ToSuccessCriteria()
        }

    }
}