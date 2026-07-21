package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.ICategoryDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.ICategoryLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.CategoryEntity

class CategoryLocalDataSourceImp(
    private val categoryDAO: ICategoryDAOContract
) : ICategoryLocalDataSource {

    override suspend fun getAll(): List<CategoryEntity> {

        return categoryDAO.getAll()
    }

    override suspend fun loadCategoryById(id: String): CategoryEntity {

        return categoryDAO.loadCategoryById(id)
    }

}