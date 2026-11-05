package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.IMenuDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.IMenuLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.MenuEntity

class MenuLocalDatasourceImp(
    private val menuDAO: IMenuDAOContract
) : IMenuLocalDataSource {

    override suspend fun getAll(): List<MenuEntity>{
        return menuDAO.getAll()
    }
}