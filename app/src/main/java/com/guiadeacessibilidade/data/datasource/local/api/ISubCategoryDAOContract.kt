package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.SubCategoryEntity

interface ISubCategoryDAOContract {

    suspend fun getAll(): List<SubCategoryEntity>

    suspend fun getSubCategoriesByCategoryId(categoryId: String): List<SubCategoryEntity>

    suspend fun loadSubCategoryById(id: String): SubCategoryEntity
}