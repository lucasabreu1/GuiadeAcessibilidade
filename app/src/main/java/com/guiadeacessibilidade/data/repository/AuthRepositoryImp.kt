package com.guiadeacessibilidade.data.repository

import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.guiadeacessibilidade.data.datasource.remote.api.IAuthRemoteDataSource
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkChecklist
import com.guiadeacessibilidade.data.datasource.remote.models.ToUser
import com.guiadeacessibilidade.data.datasource.remote.models.toChecklist
import com.guiadeacessibilidade.domain.models.Checklist
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import kotlin.math.exp

class AuthRepositoryImp(
    private val authRemoteDataSource: IAuthRemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) : IAuthRepository{
    override suspend fun login(
        email: String,
        password: String
    ): OperationResult<User> {

        return withContext(dispatcher){

            try
            {
                val loginUser = authRemoteDataSource.login(email, password)
                    ?.ToUser()

                if (loginUser == null){
                    OperationResult.Error("Error Login User")
                }
                else {
                    OperationResult.Success(loginUser)
                }
            }
            catch (exception: Exception)
            {
                OperationResult.Error("Erro no Login. Motivo: ${exception.message}")
            }
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): OperationResult<User>{

        return withContext(dispatcher){

            try
            {
                val firebaseUser = authRemoteDataSource.register(name, email, password)
                    ?.ToUser()

                if (firebaseUser == null){
                    OperationResult.Error("Error Registering this User")
                }
                else {
                    OperationResult.Success(firebaseUser)
                }
            }
            catch (exception: FirebaseAuthWeakPasswordException)
            {
                OperationResult.Error("Senha fraca, tenta usar outra.")
            }
            catch (exception: Exception)
            {
                OperationResult.Error("Erro ao tentar cadastrar esse usuário. Motivo: ${exception.message}")
            }
        }
    }

    override fun getCurrentUser(): OperationResult<User> {
        val currentUser = authRemoteDataSource.getCurrentUser()?.ToUser()

        if (currentUser == null){
            return OperationResult.Error("Nao existe usuario logado")
        }
        else {
            return OperationResult.Success(currentUser)
        }
    }

    override fun logout() {
        authRemoteDataSource.logout()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCompletedOrientationsListByUser(uid: String): Flow<OperationResult<Set<String>>> {

        return authRemoteDataSource.getCompletedOrientationsListByUser(uid)
            .mapLatest<Set<String>, OperationResult<Set<String>>> { idList ->

                OperationResult.Success(idList)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Erro desconhecido"))
            }
            .flowOn(dispatcher)
    }

    override suspend fun onToggleOrientation(
        orientationId: String,
        isCompleted: Boolean
    ): OperationResult<Boolean> {

        return withContext(dispatcher){

            try
            {
                val toggleResult = authRemoteDataSource.onToggleOrientation(orientationId, isCompleted)

                OperationResult.Success(toggleResult)
            }
            catch (exception: Exception)
            {
                OperationResult.Error("Erro ao marcar a orientação como completa. Motivo: ${exception.message}")
            }
        }
    }

    override suspend fun addCheckList(
        checkListName: String,
        checkListDescription: String,
        checkListSubCategoryId: String
    ): OperationResult<Boolean>{

        return withContext(dispatcher){

            try
            {
                val resultAddCheckList = authRemoteDataSource.addCheckList(checkListName,
                    checkListDescription, checkListSubCategoryId)

                OperationResult.Success(resultAddCheckList)
            }
            catch (exception: Exception)
            {
                OperationResult.Error("Erro ao adicionar nova orientação. Motivo: ${exception.message}")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getCheckLists(): Flow<OperationResult<List<Checklist>>> {
    /*
    * DAR UMA MELHORADA NESSE CODIGO DEPOIS
    * */
        try
        {
            val currentUser = withContext(dispatcher) {
                try
                {
                    val user = authRemoteDataSource.getCurrentUser()
                    user

                } catch (exception: Exception) {
                    throw exception
                }
            }

            return if (currentUser != null) {
                val uid = currentUser.uid

                authRemoteDataSource.getCheckLists(uid)
                    .mapLatest<List<NetworkChecklist>, OperationResult<List<Checklist>>> { listCheckList ->

                        val list = listCheckList.map { networkChecklist ->
                            networkChecklist.toChecklist()
                        }
                        OperationResult.Success(list)
                    }
                    .catch { e ->
                        emit(OperationResult.Error(e.message ?: "Erro desconhecido"))
                    }
                    .flowOn(dispatcher)

            } else {
                flow{
                    emit(OperationResult.Error("Nao existe usuario logado"))
                }.flowOn(dispatcher)
            }
        }
        catch (exception: Exception)
        {
            return flow{
                emit(OperationResult.Error(exception.message ?: "Erro desconhecido"))
            }.flowOn(dispatcher)
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun getUserCheckListCompletedOrientations(
        checkListId: String,
    ): Flow<OperationResult<Set<String>>> {

        return authRemoteDataSource.getUserCheckListCompletedOrientations (checkListId)
            .mapLatest<Set<String>, OperationResult<Set<String>>> { idList ->

                OperationResult.Success(idList)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Erro desconhecido"))
            }
            .flowOn(dispatcher)
    }

    override suspend fun onToggleCheckListOrientation(
        checkListId: String,
        orientationId: String,
        isCompleted: Boolean
    ): OperationResult<Boolean>{

        return withContext(dispatcher){

            try
            {
                val toggleResult = authRemoteDataSource.onToggleCheckListOrientation(checkListId, orientationId, isCompleted)

                OperationResult.Success(toggleResult)
            }
            catch (exception: Exception)
            {
                OperationResult.Error("Erro ao marcar a orientação como completa. Motivo: ${exception.message}")
            }
        }
    }
}