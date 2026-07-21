package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.Category
import com.guiadeacessibilidade.domain.models.CompleteSuccessCriteria
import com.guiadeacessibilidade.domain.models.SuccessCriteria
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.repository.ICategoryRepository
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import com.guiadeacessibilidade.domain.repository.IPrincipleRepository
import com.guiadeacessibilidade.domain.repository.ISuccessCriteriaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

class GetAllCategoryUseCase (
    private val categoryRepository: ICategoryRepository,
    private val dispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<OperationResult<List<Category>>>{

        return categoryRepository.getAll()
            .mapLatest<List<Category>, OperationResult<List<Category>>> { categoryList ->

                OperationResult.Success(categoryList)
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)
    }
}