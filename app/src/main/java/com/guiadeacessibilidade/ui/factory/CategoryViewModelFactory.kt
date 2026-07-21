package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.ui.viewmodel.CategoryViewModel

class CategoryViewModelFactory(
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(getAllCategoryUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}