package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation

interface IOrientationApi {

    suspend fun getAll(): List<NetworkOrientation>

    suspend fun getOrientationsBySubCategoryId(subCategoryId: String): List<NetworkOrientation>

    suspend fun loadOrientationById(id: String): NetworkOrientation?
}
