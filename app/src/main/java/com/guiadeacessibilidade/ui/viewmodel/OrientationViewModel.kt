package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.Orientation
import com.guiadeacessibilidade.domain.models.OrientationWithStatus
import com.guiadeacessibilidade.domain.models.SubCategory
import com.guiadeacessibilidade.domain.usacase.GetOrientationsBySubCategoryIdUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleOrientationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface OrientationUiState{
    object Loading: OrientationUiState
    data class Success(val orientations: List<OrientationWithStatus>) : OrientationUiState
    data class Error(val message: String) : OrientationUiState
}

sealed interface OrientationUiEvent {
    data class ShowSnackBar(val message: String): OrientationUiEvent
}


class OrientationViewModel(
    private val getOrientationsBySubCategoryIdUseCase: GetOrientationsBySubCategoryIdUseCase,
    private val toggleOrientationUseCase: ToggleOrientationUseCase,
) : ViewModel() {

    private val _uiSate = MutableStateFlow<OrientationUiState>(OrientationUiState.Loading)
    val uiState: StateFlow<OrientationUiState> = _uiSate.asStateFlow()

    private val _uiEvent = MutableSharedFlow<OrientationUiEvent>()
    val uiEvent: SharedFlow<OrientationUiEvent> = _uiEvent.asSharedFlow()

    fun loadOrientationsBySubCategoryId(subCategoryId: String){

        viewModelScope.launch {
            getOrientationsBySubCategoryIdUseCase(subCategoryId).collect { orientations ->

                when (orientations){

                    is OperationResult.Success -> {
                        _uiSate.value = OrientationUiState.Success(orientations.data)

                    }

                    is OperationResult.Error -> {
                        _uiSate.value = OrientationUiState.Error(orientations.message)
                    }
                }

            }
        }
    }

    fun onToggleOrientation(orientationId: String, isCompleted: Boolean){

        val currentState = _uiSate.value

        if (currentState is OrientationUiState.Success)
        {
            val updatedList = currentState.orientations.map { item ->
                if (item.orientation.id == orientationId){
                    item.copy(isCompleted = isCompleted)
                }
                else{
                    item
                }
            }

            _uiSate.value = OrientationUiState.Success(updatedList)

            viewModelScope.launch {

                val toggleOrientation = toggleOrientationUseCase(orientationId, isCompleted)

                when (toggleOrientation){

                    is OperationResult.Success -> {
                        _uiEvent.emit(OrientationUiEvent.ShowSnackBar("Progresso salvo com sucesso!"))
                    }

                    is OperationResult.Error -> {
                        _uiSate.value = OrientationUiState.Success(currentState.orientations)
                        _uiEvent.emit(OrientationUiEvent.ShowSnackBar("Erro ao salvar progressso, verifique sua conexao"))
                    }

                }
            }

        }
    }


}