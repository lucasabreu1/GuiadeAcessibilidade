package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.CompleteSuccessCriteria
import com.guiadeacessibilidade.domain.models.SuccessCriteria
import com.guiadeacessibilidade.domain.models.OperationResult
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

class GetAllSuccessCriteriaUseCase (
    private val successCriteriaRepository: ISuccessCriteriaRepository,
    private val guidelineRepository: IGuidelineRepository,
    private val principleRepository: IPrincipleRepository,
    private val dispatcher: CoroutineDispatcher
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(): Flow<OperationResult<List<CompleteSuccessCriteria>>>{

        return successCriteriaRepository.getAll()
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
                    OperationResult.Success(combinedList)
                }
            }
            .catch { e ->
                emit(OperationResult.Error(e.message ?: "Unkown Error"))
            }
            .flowOn(dispatcher)
    }
}