package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetCurrentUserUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): OperationResult<User> {
        return withContext(dispatcher){
            authRepository.getCurrentUser()
        }
    }
}