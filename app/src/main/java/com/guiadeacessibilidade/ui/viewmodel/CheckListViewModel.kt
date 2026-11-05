package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.Checklist
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.GetAllCheckListsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CheckListUiState{
    object Loading: CheckListUiState
    data class Success(val checkLists: List<Checklist>) : CheckListUiState
    data class Error(val message: String) : CheckListUiState
}

class CheckListViewModel (
    private val getAllCheckListUseCase: GetAllCheckListsUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<CheckListUiState>(CheckListUiState.Loading)
    val uiState: StateFlow<CheckListUiState> = _uiState.asStateFlow()

    fun loadCheckLists(){

        viewModelScope.launch {
            getAllCheckListUseCase().collect { checklist ->

                when(checklist){

                    is OperationResult.Success -> {
                        _uiState.value = CheckListUiState.Success(checklist.data)
                    }

                    is OperationResult.Error -> {
                        _uiState.value = CheckListUiState.Error(checklist.message)
                    }
                }
            }
        }
    }

}











