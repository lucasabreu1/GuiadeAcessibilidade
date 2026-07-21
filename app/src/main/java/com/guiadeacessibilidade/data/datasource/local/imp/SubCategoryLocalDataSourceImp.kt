package com.guiadeacessibilidade.data.datasource.local.imp

import com.guiadeacessibilidade.data.datasource.local.api.ISubCategoryDAOContract
import com.guiadeacessibilidade.data.datasource.local.api.ISubCategoryLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.SubCategoryEntity

class SubCategoryLocalDataSourceImp(
    private val subCategoryDAO: ISubCategoryDAOContract
) : ISubCategoryLocalDataSource {

    override suspend fun getAll(): List<SubCategoryEntity> {

        return subCategoryDAO.getAll()
    }

    override suspend fun getSubCategoriesByCategoryId(categoryId: String): List<SubCategoryEntity> {

        return subCategoryDAO.getSubCategoriesByCategoryId(categoryId)
    }

    override suspend fun loadSubCategoryById(id: String): SubCategoryEntity {

        return subCategoryDAO.loadSubCategoryById(id)
    }

}