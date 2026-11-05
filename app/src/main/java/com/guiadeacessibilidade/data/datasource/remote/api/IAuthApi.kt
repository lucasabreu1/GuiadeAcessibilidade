package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkChecklist
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkUser

interface IAuthApi {

    suspend fun login(email: String, password: String): NetworkUser?

    suspend fun register(name:String, email: String, password: String): NetworkUser?

    fun getCurrentUser(): NetworkUser?

    fun logout()

    suspend fun getCompletedOrientationsListByUser(uid: String): Set<String>

    suspend fun onToggleOrientation(orientationId: String, isCompleted: Boolean): Boolean

    suspend fun addChecklist(
        checkListName: String,
        checkListDescription: String,
        checkListSubCategoryId: String): Boolean

    suspend fun getCheckLists(uuid: String): List<NetworkChecklist>

    suspend fun getUserCheckListCompletedOrientations(checkListId: String): Set<String>

    suspend fun onToggleCheckListOrientation(checkListId: String, orientationId: String, isCompleted: Boolean): Boolean
}