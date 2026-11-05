package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkChecklist
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkOrientation
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkUser
import kotlinx.coroutines.flow.Flow

interface IAuthRemoteDataSource {

    suspend fun login(email: String, password: String): NetworkUser?

    suspend fun register(name: String, email: String, password: String): NetworkUser?

    fun getCurrentUser(): NetworkUser?

    fun logout()

    fun getCompletedOrientationsListByUser(uid: String):  Flow<Set<String>>

    suspend fun onToggleOrientation(orientationId: String, isCompleted: Boolean): Boolean

    suspend fun addCheckList(
        checkListName: String,
        checkListDescription: String,
        checkListSubCategoryId: String): Boolean

    suspend fun getCheckLists(uuid: String): Flow<List<NetworkChecklist>>

    suspend fun getUserCheckListCompletedOrientations(checkListId: String): Flow<Set<String>>

    suspend fun onToggleCheckListOrientation(checkListId: String, orientationId: String, isCompleted: Boolean): Boolean
}