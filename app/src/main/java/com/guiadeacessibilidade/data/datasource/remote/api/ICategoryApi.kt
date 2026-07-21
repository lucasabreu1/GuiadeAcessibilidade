package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkCategory

interface ICategoryApi {

    suspend fun getAll(): List<NetworkCategory>

    suspend fun loadCategoryById(id: String): NetworkCategory
}