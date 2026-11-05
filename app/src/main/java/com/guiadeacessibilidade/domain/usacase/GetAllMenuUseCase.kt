package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.Menu
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.IMenuRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class GetAllMenuUseCase(
    private val menuRepository: IMenuRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(): Flow<OperationResult<List<Menu>>> {

        return withContext(dispatcher){
            menuRepository.getAll()
        }
    }

}