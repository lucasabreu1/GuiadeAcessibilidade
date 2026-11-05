package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(email: String, password: String) : OperationResult<User> {

        return withContext(dispatcher) {
            if (email.isBlank() || password.isBlank()) {
                OperationResult.Error("Email e senha nao podem ser vazios!")
            } else {
                authRepository.login(email, password)
            }
        }
    }
}