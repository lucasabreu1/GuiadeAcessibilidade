package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToSubCategory
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import com.guiadeacessibilidade.data.datasource.remote.models.ToSubCategoryEntity
import com.guiadeacessibilidade.domain.models.Category
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class SubCategoryRepositoryImp (
    //private val localDataSource: ISubCategoryLocalDataSource,
    private val remoteDataSource: ISubCategoryRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : ISubCategoryRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAll(): Flow<OperationResult<List<SubCategory>>>  {

        return remoteDataSource.getAll()
            .mapLatest<List<NetworkSubCategory>, OperationResult<List<SubCategory>>> { subCategoryList ->

                val list = subCategoryList.map { networkSubCategory ->
                    networkSubCategory.ToSubCategoryEntity().ToSubCategory()
                }
                OperationResult.Success(list)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getSubCategoriesByCategoryId(categoryId: String): Flow<OperationResult<List<SubCategory>>> {

        return remoteDataSource.getSubCategoriesByCategoryId(categoryId)
            .mapLatest<List<NetworkSubCategory>, OperationResult<List<SubCategory>>> { subCategoryList ->

                val list = subCategoryList.map { networkSubCategory ->
                    networkSubCategory.ToSubCategoryEntity().ToSubCategory()
                }
                OperationResult.Success(list)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)

    }

    override suspend fun loadSubCategoryById(id: String): SubCategory {

        return withContext(dispatcher){
            remoteDataSource.loadSubCategoryById(id).ToSubCategoryEntity().ToSubCategory()
        }

    }
}