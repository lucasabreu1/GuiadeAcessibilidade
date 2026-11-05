package com.guiadeacessibilidade.domain.usacase


import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import com.guiadeacessibilidade.domain.repository.IOrientationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ToggleOrientationUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(orientationId: String, isCompleted: Boolean): OperationResult<Boolean> {

        return withContext(dispatcher){
            authRepository.onToggleOrientation(orientationId, isCompleted)
        }
    }
}