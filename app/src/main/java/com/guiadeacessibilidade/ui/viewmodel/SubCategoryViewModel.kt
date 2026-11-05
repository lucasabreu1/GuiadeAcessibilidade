package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.usacase.GetSubCategoriesByCategoryIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface SubCategoryUiState{
    object Loading: SubCategoryUiState
    data class Success(val subCategories: List<SubCategory>) : SubCategoryUiState
    data class Error(val message: String) : SubCategoryUiState
}


class SubCategoryViewModel(
    private val getSubCategoriesByCategoryIdUseCase: GetSubCategoriesByCategoryIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SubCategoryUiState>(SubCategoryUiState.Loading)

    val uiState: StateFlow<SubCategoryUiState> = _uiState.asStateFlow()

    fun loadSubCategoriesByCategoryId(categoryId: String){

        _uiState.value = SubCategoryUiState.Loading

        viewModelScope.launch {
            getSubCategoriesByCategoryIdUseCase(categoryId).collect { subCategories ->

                when (subCategories) {
                    is OperationResult.Success -> {
                        _uiState.value = SubCategoryUiState.Success(subCategories.data)
                    }

                    is OperationResult.Error -> {
                        _uiState.value = SubCategoryUiState.Error("Ocorreu um erro")
                    }
                }
            }
        }
    }

}