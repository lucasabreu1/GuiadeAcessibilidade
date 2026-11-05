package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.LoadOrientationByIdUseCase
import com.guiadeacessibilidade.ui.viewmodel.OrientationItemViewModel

class OrientationItemViewModelFactory (
    private val loadOrientationByIdUseCase: LoadOrientationByIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrientationItemViewModel::class.java)) {
            return OrientationItemViewModel(loadOrientationByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}