package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

interface ISuccessCriteriaLocalDataSource {

    suspend fun getAll(): List<SuccessCriteriaEntity>

    suspend fun loadSuccessCriteriaById(id: String): SuccessCriteriaEntity
}