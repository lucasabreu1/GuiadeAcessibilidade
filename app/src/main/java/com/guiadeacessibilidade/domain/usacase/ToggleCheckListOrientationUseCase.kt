package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ToggleCheckListOrientationUseCase (
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke(checkListId: String, orientationId: String, isCompleted: Boolean): OperationResult<Boolean> {

        return withContext(dispatcher){
            authRepository.onToggleCheckListOrientation(checkListId, orientationId, isCompleted)
        }
    }
}