package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineApi
import com.guiadeacessibilidade.data.datasource.remote.api.IGuidelineRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleApi
import com.guiadeacessibilidade.data.datasource.remote.api.IPrincipleRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkGuideline
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkPrinciple
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PrincipleRemoteDataSourceImp(
    private val principleApi: IPrincipleApi,
    private val dispatcher: CoroutineDispatcher,
) : IPrincipleRemoteDataSource{

    override suspend fun loadPrincipleById(id: String): NetworkPrinciple {

        val principle = principleApi.loadPrincipleById(id)
        return principle
    }

}