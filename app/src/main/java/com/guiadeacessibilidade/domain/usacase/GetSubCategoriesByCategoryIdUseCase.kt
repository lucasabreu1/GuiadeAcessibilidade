package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.repository.ISubCategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSubCategoriesByCategoryIdUseCase (
    private val subCategoryRepository: ISubCategoryRepository,
    private val dispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(categoryId: String): Flow<OperationResult<List<SubCategory>>>{

        return subCategoryRepository.getSubCategoriesByCategoryId(categoryId)
            .flowOn(dispatcher)
    }
}