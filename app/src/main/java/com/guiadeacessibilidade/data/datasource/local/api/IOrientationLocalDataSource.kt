package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.OrientationEntity

interface IOrientationLocalDataSource {

    suspend fun getAll(): List<OrientationEntity>

    suspend fun getOrientationsBySubCategoryId(subCategoryId: String): List<OrientationEntity>

    suspend fun loadOrientationById(id: String): OrientationEntity
}