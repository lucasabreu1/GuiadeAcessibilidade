package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkPrinciple
import kotlinx.coroutines.flow.Flow

interface IPrincipleRemoteDataSource {

    suspend fun loadPrincipleById(id: String): NetworkPrinciple
}