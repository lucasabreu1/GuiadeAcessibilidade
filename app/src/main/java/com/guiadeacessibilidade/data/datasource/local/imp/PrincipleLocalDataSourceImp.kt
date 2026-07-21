package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.IPrincipleDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.IPrincipleLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.PrincipleEntity

class PrincipleLocalDataSourceImp(
    private val principleDAO: IPrincipleDAOContract
) : IPrincipleLocalDataSource {


    override suspend fun loadGuidelineById(id: String): PrincipleEntity {
        return principleDAO.loadPrincipleById(id)
    }

}