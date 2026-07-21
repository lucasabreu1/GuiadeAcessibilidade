package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria

interface ISuccessCriteriaApi {

    suspend fun getAll(): List<NetworkSuccessCriteria>

    suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria
}