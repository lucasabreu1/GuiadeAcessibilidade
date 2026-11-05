package com.guiadeacessibilidade.domain.usacase

import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.repository.IOrientationRepository

class LoadOrientationByIdUseCase(
    private val orientationRepository: IOrientationRepository,
) {
    suspend operator fun invoke(orientationId: String): OperationResult<Orientation>{
        return orientationRepository.loadOrientationById(orientationId)
    }
}