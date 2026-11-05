package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.IMenuApi
import com.guiadeacessibilidade.data.datasource.remote.api.IMenuRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkMenu
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MenuRemoteDataSourceImp (
    private val menuApi: IMenuApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 10000
) : IMenuRemoteDataSource{
    override suspend fun getAll(): Flow<List<NetworkMenu>> {

        return flow {
            while (true){
                val menuList = menuApi.getAll()
                emit(menuList)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }
}