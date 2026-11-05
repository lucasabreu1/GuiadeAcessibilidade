package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.IOrientationDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.IOrientationLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.OrientationEntity

class OrientationLocalDataSourceImp(
    private val orientationDAO: IOrientationDAOContract
) : IOrientationLocalDataSource {

    override suspend fun getAll(): List<OrientationEntity> {

        return orientationDAO.getAll()
    }

    override suspend fun getOrientationsBySubCategoryId(subCategoryId: String): List<OrientationEntity> {
        return orientationDAO.getOrientationsBySubCategoryId(subCategoryId)
    }

    override suspend fun loadOrientationById(id: String): OrientationEntity {
        return orientationDAO.loadOrientationById(id)
    }
}