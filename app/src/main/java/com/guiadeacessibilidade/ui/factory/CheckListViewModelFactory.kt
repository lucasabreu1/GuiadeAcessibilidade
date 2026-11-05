package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllCheckListsUseCase
import com.guiadeacessibilidade.ui.viewmodel.CheckListViewModel

class CheckListViewModelFactory(
    private val getAllCheckListsUseCase: GetAllCheckListsUseCase
) : ViewModelProvider.Factory{

    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckListViewModel::class.java)) {
            return CheckListViewModel(getAllCheckListsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}