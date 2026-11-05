package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

interface ISuccessCriteriaDAOContract {

    suspend fun getAll(): List<SuccessCriteriaEntity>

    suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<SuccessCriteriaEntity>
    suspend fun loadSuccessCriteriaById(id: String): SuccessCriteriaEntity
}