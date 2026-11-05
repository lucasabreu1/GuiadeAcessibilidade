package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.CompleteSuccessCriteria
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SuccessCriteria
import com.guiadeacessibilidade.domain.repository.IGuidelineRepository
import com.guiadeacessibilidade.domain.repository.IPrincipleRepository
import com.guiadeacessibilidade.domain.repository.ISuccessCriteriaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import okhttp3.Dispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class GetSuccessCriteriasBySubCategoryIdUseCase(
    private val successCriteriaRepository: ISuccessCriteriaRepository,
    private val guidelineRepository: IGuidelineRepository,
    private val principleRepository: IPrincipleRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(subCategoryId: String): Flow<OperationResult<List<CompleteSuccessCriteria>>>{

        return successCriteriaRepository.getSuccessCriteriasBySubCategoryId(subCategoryId)
            .mapLatest<List<SuccessCriteria>, OperationResult<List<CompleteSuccessCriteria>>> { successCriteriaList ->

                coroutineScope {
                    val deferredCompleteSuccessCriteria = successCriteriaList.map { successCriteria ->
                        async {
                            val deferredGuideline = async { guidelineRepository.loadGuidelineById(successCriteria.guidelineId) }
                            val deferredPrinciple = async { principleRepository.loadPrincipleById(successCriteria.principleId) }

                            val guideline = deferredGuideline.await()
                            val principle = deferredPrinciple.await()

                            CompleteSuccessCriteria(successCriteria, guideline, principle)
                        }
                    }

                    val combinedList = deferredCompleteSuccessCriteria.awaitAll()
                    OperationResult.Success(data = combinedList)
                }
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)
    }
}