package com.guiadeacessibilidade.data.repository

import com.guiadeacessibilidade.data.datasource.local.api.IMenuLocalDataSource
import com.guiadeacessibilidade.data.datasource.local.entities.toMenu
import com.guiadeacessibilidade.data.datasource.remote.api.IMenuRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkMenu
import com.guiadeacessibilidade.data.datasource.remote.models.ToMenuEntity
import com.guiadeacessibilidade.domain.models.Menu
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IMenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

class MenuRepositoryImp(
    //private val localDataSource: IMenuLocalDataSource,
    private val remoteDataSource: IMenuRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : IMenuRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getAll(): Flow<OperationResult<List<Menu>>> {

        return remoteDataSource.getAll()
            .mapLatest<List<NetworkMenu>, OperationResult<List<Menu>>> { menuList ->

                val list  = menuList.map { networkMenu ->
                    networkMenu.ToMenuEntity().toMenu()
                }
                OperationResult.Success(list)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Erro ao recuperar os itens de Menu."))
            }
            .flowOn(dispatcher)
    }

}















