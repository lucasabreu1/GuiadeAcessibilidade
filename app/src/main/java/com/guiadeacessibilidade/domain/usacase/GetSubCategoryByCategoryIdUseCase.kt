package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest

class GetSubCategoryByCategoryIdUseCase (
    private val subCategoryRepository: ISubCategoryRepository,
    private val dispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(categoryId: String): Flow<OperationResult<List<SubCategory>>>{

        return subCategoryRepository.getSubCategoriesByCategoryId(categoryId)
            .flowOn(dispatcher)
    }
}