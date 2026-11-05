package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.entities.ToOrientation
import com.guiadeacessibilidade.data.datasource.local.entities.ToSubCategory
import com.guiadeacessibilidade.data.datasource.remote.api.IOrientationRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import com.guiadeacessibilidade.data.datasource.remote.models.ToOrientationEntity
import com.guiadeacessibilidade.data.datasource.remote.models.ToSubCategoryEntity
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.repository.IOrientationRepository
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext

class OrientationRepositoryImp (
    //private val localDataSource: IOrientationLocalDataSource,
    private val remoteDataSource: IOrientationRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : IOrientationRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAll(): Flow<OperationResult<List<Orientation>>>  {

        return remoteDataSource.getAll()
            .mapLatest<List<NetworkOrientation>, OperationResult<List<Orientation>>> { orientationList ->

                val list = orientationList.map { networkOrientation ->
                    networkOrientation.ToOrientationEntity().ToOrientation()
                }
                OperationResult.Success(list)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getOrientationsBySubCategoryId(subCategoryId: String): Flow<OperationResult<List<Orientation>>> {

        return remoteDataSource.getOrientationsBySubCategoryId(subCategoryId)
            .mapLatest<List<NetworkOrientation>, OperationResult<List<Orientation>>> { orientationList ->

                val list = orientationList.map { networkOrientation ->
                    networkOrientation.ToOrientationEntity().ToOrientation()
                }
                OperationResult.Success(list)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)

    }

    override suspend fun loadOrientationById(id: String): OperationResult<Orientation> {

        return withContext(dispatcher){
            val orientationItem = remoteDataSource.loadOrientationById(id)?.ToOrientationEntity()
                ?.ToOrientation()

            if (orientationItem == null) {
                OperationResult.Error("Erro ao carregar orientações")
            }
            else{
                OperationResult.Success(orientationItem)
            }
        }
    }
}