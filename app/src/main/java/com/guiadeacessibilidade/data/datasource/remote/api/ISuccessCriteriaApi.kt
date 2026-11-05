package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSuccessCriteria

interface ISuccessCriteriaApi {

    suspend fun getAll(): List<NetworkSuccessCriteria>

    suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<NetworkSuccessCriteria>
    suspend fun loadSuccessCriteriaById(id: String): NetworkSuccessCriteria
}