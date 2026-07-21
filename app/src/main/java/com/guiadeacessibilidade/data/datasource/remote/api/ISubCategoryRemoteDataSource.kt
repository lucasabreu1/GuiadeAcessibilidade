package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.local.entities.SubCategoryEntity
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import kotlinx.coroutines.flow.Flow

interface ISubCategoryRemoteDataSource {

    fun getAll(): Flow<List<NetworkSubCategory>>

    fun getSubCategoriesByCategoryId(categoryId: String): Flow<List<NetworkSubCategory>>

    suspend fun loadSubCategoryById(id: String): NetworkSubCategory

}