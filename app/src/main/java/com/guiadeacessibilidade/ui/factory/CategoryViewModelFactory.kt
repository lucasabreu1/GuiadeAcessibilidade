package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetCurrentUserUseCase
import com.guiadeacessibilidade.domain.usacase.LogoutUserUseCase
import com.guiadeacessibilidade.ui.viewmodel.CategoryViewModel

class CategoryViewModelFactory(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            return CategoryViewModel(getAllCategoryUseCase, getCurrentUserUseCase, logoutUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}