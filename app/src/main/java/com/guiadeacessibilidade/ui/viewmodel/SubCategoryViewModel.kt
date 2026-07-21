package com.guiadeacessibilidade.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.usacase.GetAllSubCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetSubCategoryByCategoryIdUseCase
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
    private val getSubCategoryByCategoryIdUseCase: GetSubCategoryByCategoryIdUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SubCategoryUiState>(SubCategoryUiState.Loading)

    val uiState: StateFlow<SubCategoryUiState> = _uiState.asStateFlow()

    fun loadSubCategoriesByCategoryId(categoryId: String){

        _uiState.value = SubCategoryUiState.Loading

        viewModelScope.launch {
            getSubCategoryByCategoryIdUseCase(categoryId).collect { subCategories ->

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