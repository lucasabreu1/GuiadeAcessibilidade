package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetOrientationsBySubCategoryIdUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleOrientationUseCase
import com.guiadeacessibilidade.ui.viewmodel.OrientationViewModel

class OrientationViewModelFactory(
    private val getOrientationsBySubCategoryIdUseCase: GetOrientationsBySubCategoryIdUseCase,
    private val toggleOrientationUseCase: ToggleOrientationUseCase
) : ViewModelProvider.Factory {

    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrientationViewModel::class.java)) {
            return OrientationViewModel(getOrientationsBySubCategoryIdUseCase, toggleOrientationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}