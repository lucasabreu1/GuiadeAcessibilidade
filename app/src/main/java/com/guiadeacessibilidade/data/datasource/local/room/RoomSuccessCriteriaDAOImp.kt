package com.guiadeacessibilidade.data.datasource.local.room

import com.guiadeacessibilidade.data.datasource.local.api.ISuccessCriteriaDAOContract
import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

class RoomSuccessCriteriaDAOImp(
    private val dao: SuccessCriteriaDAO
) : ISuccessCriteriaDAOContract {

    override suspend fun getAll(): List<SuccessCriteriaEntity> {
        return dao.getAll()
    }

    override suspend fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<SuccessCriteriaEntity> {
        return dao.getSuccessCriteriasBySubCategoryId(subCategoryId)
    }

    override suspend fun loadSuccessCriteriaById(id: String): SuccessCriteriaEntity {
        return dao.loadSuccessCriteriaById(id)
    }
}