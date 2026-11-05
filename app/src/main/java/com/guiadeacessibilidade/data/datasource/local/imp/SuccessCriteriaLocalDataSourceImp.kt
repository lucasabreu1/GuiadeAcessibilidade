package com.guiadeacessibilidade.data.datasource.local.imp


import com.guiadeacessibilidade.data.datasource.local.api.ISuccessCriteriaDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.ISuccessCriteriaLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

class SuccessCriteriaLocalDataSourceImp(
    private val successCriteriaDAO: ISuccessCriteriaDAOContract
) : ISuccessCriteriaLocalDataSource {

    override suspend fun getAll(): List<SuccessCriteriaEntity> {

        return successCriteriaDAO.getAll()
    }

    override suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<SuccessCriteriaEntity> {

        return successCriteriaDAO.getSuccessCriteriasBySubCategoryId(subCategoryId)
    }

    override suspend fun loadSuccessCriteriaById(id: String): SuccessCriteriaEntity {

        return successCriteriaDAO.loadSuccessCriteriaById(id)
    }

}