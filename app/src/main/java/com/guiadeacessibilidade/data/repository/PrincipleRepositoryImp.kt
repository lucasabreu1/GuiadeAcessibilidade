package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToGuideline
import com.guiadeacessibilidade.data.datasource.local.entities.ToPrinciple
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.ToGuidelineEntity
import com.guiadeacessibilidade.data.datasource.remote.models.ToPrincipleEntity
import com.guiadeacessibilidade.domain.models.Guideline
import com.guiadeacessibilidade.domain.models.Principle
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import com.guiadeacessibilidade.domain.repository.IPrincipleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PrincipleRepositoryImp(
//    private val localDataSource: IPrincipleRemoteDataSource
    private val remoteDataSource: IPrincipleRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : IPrincipleRepository {

    override suspend fun loadPrincipleById(id: String): Principle {

        return withContext(dispatcher){
            remoteDataSource.loadPrincipleById(id).ToPrincipleEntity().ToPrinciple()
        }
    }
}