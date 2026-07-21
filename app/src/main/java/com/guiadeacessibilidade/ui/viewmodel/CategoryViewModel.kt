package com.guiadeacessibilidade.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.Category
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CategoryUiState {
    object Loading: CategoryUiState
    data class Success(val categories: List<Category>) : CategoryUiState
    data class Error(val message: String) : CategoryUiState
}

class CategoryViewModel(
    private val getAllCategoryUseCase: GetAllCategoryUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    fun loadCategories(){

        _uiState.value = CategoryUiState.Loading

        viewModelScope.launch {
            getAllCategoryUseCase().collect { categories ->

                when (categories){
                    is OperationResult.Success -> {
                        _uiState.value = CategoryUiState.Success(categories.data)
                    }

                    is OperationResult.Error -> { categories
                        _uiState.value = CategoryUiState.Error("Ocorreu um Erro.")
                    }
                }
            }
        }
    }
}