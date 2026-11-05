package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.Checklist
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {

    suspend fun login(email: String, password: String) : OperationResult<User>

    suspend fun register(name: String, email: String, password: String) : OperationResult<User>

    fun getCurrentUser(): OperationResult<User>

    fun logout()

    fun getCompletedOrientationsListByUser(uid: String) : Flow<OperationResult<Set<String>>>

    suspend fun onToggleOrientation(orientationId: String, isCompleted: Boolean): OperationResult<Boolean>

    suspend fun addCheckList(
        checkListName: String,
        checkListDescription: String,
        checkListSubCategoryId: String): OperationResult<Boolean>
    suspend fun getCheckLists(): Flow<OperationResult<List<Checklist>>>

    suspend fun getUserCheckListCompletedOrientations(checkListId: String): Flow<OperationResult<Set<String>>>

    suspend fun onToggleCheckListOrientation(checkListId: String, orientationId: String, isCompleted: Boolean): OperationResult<Boolean>

}