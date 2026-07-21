package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria
import kotlinx.coroutines.flow.Flow

interface ISuccessCriteriaRemoteDataSource {

    fun getAll(): Flow<List<NetworkSuccessCriteria>>

    suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria

}