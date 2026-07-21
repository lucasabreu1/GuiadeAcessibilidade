package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.local.entities.SubCategoryEntity
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory

interface ISubCategoryApi {

    suspend fun getAll(): List<NetworkSubCategory>

    suspend fun getSubCategoriesByCategoryId(categoryId: String): List<NetworkSubCategory>

    suspend fun loadSubCategoryById(id: String): NetworkSubCategory
}