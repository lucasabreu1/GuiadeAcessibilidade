package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetOrientationsBySubCategoryIdUseCase
import com.guiadeacessibilidade.domain.usacase.GetOrientationsByUserChecklistUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleCheckListOrientationUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleOrientationUseCase
import com.guiadeacessibilidade.ui.viewmodel.OrientationViewModel
import com.guiadeacessibilidade.ui.viewmodel.ProgressCheckListViewModel

class ProgressCheckListViewModelFactory(
    private val getOrientationsByUserCheckListUseCase: GetOrientationsByUserChecklistUseCase,
    private val toggleCheckListOrientationUseCase: ToggleCheckListOrientationUseCase
) : ViewModelProvider.Factory {

    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgressCheckListViewModel::class.java)) {
            return ProgressCheckListViewModel(
                getOrientationsByUserCheckListUseCase,
                toggleCheckListOrientationUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

