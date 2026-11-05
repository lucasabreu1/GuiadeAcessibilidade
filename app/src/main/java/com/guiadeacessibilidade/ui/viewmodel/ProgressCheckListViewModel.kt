package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.OrientationWithStatus
import com.guiadeacessibilidade.domain.usacase.GetOrientationsByUserChecklistUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleCheckListOrientationUseCase
import com.guiadeacessibilidade.domain.usacase.ToggleOrientationUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface ProgressOrientationUiState{
    object Loading: ProgressOrientationUiState
    data class Success(val orientations: List<OrientationWithStatus>) : ProgressOrientationUiState
    data class Error(val message: String) : ProgressOrientationUiState
}

sealed interface ProgressOrientationUiEvent {
    data class ShowSnackBar(val message: String): ProgressOrientationUiEvent
}

class ProgressCheckListViewModel(
    private val getOrientationsByUserCheckListUseCase: GetOrientationsByUserChecklistUseCase,
    private val toggleCheckListOrientationUseCase: ToggleCheckListOrientationUseCase
) : ViewModel(){

    private val _uiSate = MutableStateFlow<ProgressOrientationUiState>(ProgressOrientationUiState.Loading)
    val uiState: StateFlow<ProgressOrientationUiState> = _uiSate.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProgressOrientationUiEvent>()
    val uiEvent: SharedFlow<ProgressOrientationUiEvent> = _uiEvent.asSharedFlow()

    fun getOrientationsByUserCheckList(checkListId: String, subCategoryId: String){

        viewModelScope.launch {
            getOrientationsByUserCheckListUseCase(checkListId, subCategoryId).collect { orientations ->

                when (orientations){

                    is OperationResult.Success -> {
                        _uiSate.value = ProgressOrientationUiState.Success(orientations.data)

                    }

                    is OperationResult.Error -> {
                        _uiSate.value = ProgressOrientationUiState.Error(orientations.message)
                    }
                }

            }
        }
    }

    fun onToggleCheckListOrientation(checkListId: String, orientationId: String, isCompleted: Boolean){

        val currentState = _uiSate.value

        if (currentState is ProgressOrientationUiState.Success)
        {
            val updatedList = currentState.orientations.map { item ->
                if (item.orientation.id == orientationId){
                    item.copy(isCompleted = isCompleted)
                }
                else{
                    item
                }
            }

            _uiSate.value = ProgressOrientationUiState.Success(updatedList)

            viewModelScope.launch {

                val toggleOrientation = toggleCheckListOrientationUseCase(checkListId, orientationId, isCompleted)

                when (toggleOrientation){

                    is OperationResult.Success -> {
                        _uiEvent.emit(ProgressOrientationUiEvent.ShowSnackBar("Progresso salvo com sucesso!"))
                    }

                    is OperationResult.Error -> {
                        _uiSate.value = ProgressOrientationUiState.Success(currentState.orientations)
                        _uiEvent.emit(ProgressOrientationUiEvent.ShowSnackBar("Erro ao salvar progressso, verifique sua conexao"))
                    }

                }
            }

        }
    }

}





