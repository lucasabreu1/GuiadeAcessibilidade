package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.models.OrientationWithStatus
import com.guiadeacessibilidade.domain.repository.IAuthRepository
import com.guiadeacessibilidade.domain.repository.IOrientationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class GetOrientationsBySubCategoryIdUseCase(
    private val orientationRepository: IOrientationRepository,
    private val authRepository: IAuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
     operator fun invoke(subCategoryId: String): Flow<OperationResult<List<OrientationWithStatus>>> {

         return flow {

             when(val userResult = authRepository.getCurrentUser()){

                 is OperationResult.Error -> {
                     emit(OperationResult.Error(userResult.message))
                 }
                 is OperationResult.Success -> {
                     val userUid = userResult.data.uid

                     combine(
                         orientationRepository.getOrientationsBySubCategoryId(subCategoryId),
                         authRepository.getCompletedOrientationsListByUser(userUid)
                     ) { orientationsResult, idsResult ->

                         when {
                             orientationsResult is OperationResult.Error -> orientationsResult
                             idsResult is OperationResult.Error -> idsResult

                             orientationsResult is OperationResult.Success && idsResult is OperationResult.Success -> {
                                 val combinedList = orientationsResult.data.map { orientation ->
                                     OrientationWithStatus(
                                         orientation = orientation,
                                         isCompleted = idsResult.data.contains(orientation.id)
                                     )
                                 }
                                 OperationResult.Success(combinedList)
                             }
                             else -> OperationResult.Error("Algum outro erro")
                         }

                     }.collect { combinedResult ->
                         emit(combinedResult)
                     }
                 }
             }
         }
         .flowOn(dispatcher)
    }
}