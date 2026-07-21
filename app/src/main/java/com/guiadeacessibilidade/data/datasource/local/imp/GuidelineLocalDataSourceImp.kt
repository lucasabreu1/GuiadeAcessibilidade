package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.IGuidelineDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.IGuidelineLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.GuidelineEntity

class GuidelineLocalDataSourceImp(
    private val guidelineDAO: IGuidelineDAOContract
) : IGuidelineLocalDataSource {

    override suspend fun getAll(): List<GuidelineEntity> {
        return guidelineDAO.getAll()
    }

    override suspend fun loadGuidelineById(id: String): GuidelineEntity {
        return guidelineDAO.loadGuidelineById(id)
    }

}