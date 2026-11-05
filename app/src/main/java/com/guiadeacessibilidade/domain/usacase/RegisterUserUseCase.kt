package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RegisterUserUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(name: String, email: String, password: String) : OperationResult<User>{

        return withContext(dispatcher) {
            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                OperationResult.Error("Email, senha e nome não podem ser vazios!")
            } else {
                authRepository.register(name, email, password)
            }
        }
    }
}