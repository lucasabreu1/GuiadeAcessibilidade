package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class AddCheckListUseCase(
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        checkListName: String,
        checkListDescription: String,
        checkListSubcategoryId: String
    ): OperationResult<Boolean> {

        return withContext(dispatcher){
            if (checkListName.isBlank() || checkListDescription.isBlank()) {

                OperationResult.Error("Nome e descrição do checklist não podem ser vazios!")
            }
            else{
                authRepository.addCheckList(checkListName, checkListDescription, checkListSubcategoryId)
            }
        }
    }
}