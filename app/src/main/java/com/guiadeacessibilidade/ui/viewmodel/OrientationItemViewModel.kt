package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.usacase.LoadOrientationByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface OrientationItemUiState{
    object Loading: OrientationItemUiState
    data class Success(val orientationItem: Orientation): OrientationItemUiState
    data class Error(val message: String) : OrientationItemUiState
}

class OrientationItemViewModel(
    private val loadOrientationByIdUseCase: LoadOrientationByIdUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<OrientationItemUiState>(OrientationItemUiState.Loading)

    val uiState: StateFlow<OrientationItemUiState> = _uiState.asStateFlow()

    fun loadOrientationById(orientationId: String){

        _uiState.value = OrientationItemUiState.Loading

        viewModelScope.launch {
            val orientationItem = loadOrientationByIdUseCase(orientationId)

            when (orientationItem){

                is OperationResult.Success -> {
                    _uiState.value = OrientationItemUiState.Success(orientationItem.data)
                }

                is OperationResult.Error -> {
                    _uiState.value = OrientationItemUiState.Error("Ocorreu um erro")
                }
            }
        }
    }

}