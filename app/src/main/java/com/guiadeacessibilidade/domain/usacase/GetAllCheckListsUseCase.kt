package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.Checklist
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetAllCheckListsUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
){
    suspend operator fun invoke() : Flow<OperationResult<List<Checklist>>> {
        return withContext(dispatcher){
            authRepository.getCheckLists()
        }
    }
}