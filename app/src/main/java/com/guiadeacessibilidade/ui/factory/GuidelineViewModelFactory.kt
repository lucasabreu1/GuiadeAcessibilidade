package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllSuccessCriteriaUseCase
import com.guiadeacessibilidade.ui.viewmodel.SuccessCriteriaViewModel

class GuidelineViewModelFactory(
    private val getAllGuidelinesUseCase: GetAllSuccessCriteriaUseCase
) : ViewModelProvider.Factory {

//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SuccessCriteriaViewModel::class.java)) {
//            return SuccessCriteriaViewModel(getAllGuidelinesUseCase) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
}