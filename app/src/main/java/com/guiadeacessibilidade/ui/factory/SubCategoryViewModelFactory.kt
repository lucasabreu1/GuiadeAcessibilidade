package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetAllSubCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetSubCategoryByCategoryIdUseCase
import com.guiadeacessibilidade.ui.viewmodel.SubCategoryViewModel

class SubCategoryViewModelFactory(
    private val getSubCategoryByCategoryIdUseCase: GetSubCategoryByCategoryIdUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubCategoryViewModel::class.java)) {
            return SubCategoryViewModel(getSubCategoryByCategoryIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}