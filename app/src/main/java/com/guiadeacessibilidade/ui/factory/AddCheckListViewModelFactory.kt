package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.AddCheckListUseCase
import com.guiadeacessibilidade.ui.viewmodel.AddCheckListViewModel


class AddCheckListViewModelFactory(
    private val addCheckListUseCase: AddCheckListUseCase
) : ViewModelProvider.Factory{
    override fun<T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddCheckListViewModel::class.java)) {
            return AddCheckListViewModel(addCheckListUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}