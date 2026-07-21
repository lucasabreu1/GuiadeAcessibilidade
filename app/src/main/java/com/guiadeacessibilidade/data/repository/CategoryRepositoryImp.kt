package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToCategory
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.ToCategoryEntity
import com.guiadeacessibilidade.domain.models.Category
import com.guiadeacessibilidade.domain.repository.ICategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class CategoryRepositoryImp (
    //private val localDataSource: ICategoryLocalDataSource,
    private val remoteDataSource: ICategoryRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : ICategoryRepository {

    override suspend fun getAll(): Flow<List<Category>> {

        return remoteDataSource.getAll().map { categoryList ->
            categoryList.map { networkCategory ->
                networkCategory.ToCategoryEntity().ToCategory()
            }
        }.flowOn(dispatcher)
    }

    override suspend fun loadCategoryById(id: String): Category {

        return withContext(dispatcher){
            remoteDataSource.loadCategoryById(id).ToCategoryEntity().ToCategory()
        }

    }
}