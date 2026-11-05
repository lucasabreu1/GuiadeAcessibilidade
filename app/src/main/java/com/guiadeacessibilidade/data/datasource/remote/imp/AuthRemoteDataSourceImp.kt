package com.guiadeacessibilidade.data.datasource.remote.imp

import com.guiadeacessibilidade.data.datasource.remote.api.IAuthApi
import com.guiadeacessibilidade.data.datasource.remote.api.IAuthRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkChecklist
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

 class AuthRemoteDataSourceImp(
    private val authApi: IAuthApi,
    private val dispatcher: CoroutineDispatcher,
    private val refreshIntervalMs: Long = 3600000
) : IAuthRemoteDataSource {
    override suspend fun login(
        email: String,
        password: String
    ): NetworkUser? {

        return withContext(dispatcher){
            authApi.login(email, password)
        }

    }

    override suspend fun register(name: String, email: String, password: String): NetworkUser? {

        return withContext(dispatcher){
            authApi.register(name, email, password)
        }
    }

    override fun getCurrentUser(): NetworkUser? {
        return authApi.getCurrentUser()
    }

    override fun logout() {
        authApi.logout()
    }

    override fun getCompletedOrientationsListByUser(uid: String): Flow<Set<String>> {

        return flow {
            while(true){
                val listIds = authApi.getCompletedOrientationsListByUser(uid)
                emit(listIds)
                delay(refreshIntervalMs)

            }
        }.flowOn(dispatcher)
    }

    override suspend fun onToggleOrientation(
        orientationId: String,
        isCompleted: Boolean
    ): Boolean {
        return withContext(dispatcher){
            authApi.onToggleOrientation(orientationId, isCompleted)
        }
    }

    override suspend fun addCheckList(
        checkListName: String,
        checkListDescription: String,
        checkListSubcategoryId: String
    ): Boolean {
        return withContext(dispatcher){
            authApi.addChecklist(checkListName, checkListDescription, checkListSubcategoryId)
        }
    }

    override suspend fun getCheckLists(uuid: String): Flow<List<NetworkChecklist>> {
        return flow {
            while (true){
                val checkLists = authApi.getCheckLists(uuid)
                emit(checkLists)
                delay(refreshIntervalMs)
            }
        }.flowOn(dispatcher)
    }

     override suspend fun getUserCheckListCompletedOrientations(checkListId: String): Flow<Set<String>> {
         return flow{
             while(true){
                 val checkListOrientationsList = authApi.getUserCheckListCompletedOrientations(checkListId)
                 emit(checkListOrientationsList)
                 delay(refreshIntervalMs)
             }
         }
     }

     override suspend fun onToggleCheckListOrientation(
         checkListId: String,
         orientationId: String,
         isCompleted: Boolean
     ): Boolean {
         return withContext(dispatcher){
             authApi.onToggleCheckListOrientation(checkListId, orientationId, isCompleted)
         }
     }
 }

//return withContext(dispatcher){
//    authApi.onToggleOrientation(orientationId, isCompleted)
//}