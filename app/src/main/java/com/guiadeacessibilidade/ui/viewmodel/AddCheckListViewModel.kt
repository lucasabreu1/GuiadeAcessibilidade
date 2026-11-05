package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.AddCheckListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface AddCheckListUiState{
    object Idle: AddCheckListUiState
    data object Loading: AddCheckListUiState
    data class Error(val message: String): AddCheckListUiState
}

data class DropDownSubCategory(
    val optionSelected: String = "",
    val expanded: Boolean = false
)

class AddCheckListViewModel(
    private val addCheckListUseCase: AddCheckListUseCase
) : ViewModel(){

    private val _checkListName = MutableStateFlow("")
    val checkListName: StateFlow<String> = _checkListName.asStateFlow()

    private val _checkListDescription = MutableStateFlow("")
    val checkListDescription: StateFlow<String> = _checkListDescription.asStateFlow()

    private val _checkListSubCategoryId = MutableStateFlow("")
    val checkListSubCategoryId: StateFlow<String> = _checkListSubCategoryId.asStateFlow()


    private val _dropDownSubCategory = MutableStateFlow<DropDownSubCategory>(DropDownSubCategory())
    val dropDownSubCategory: StateFlow<DropDownSubCategory> = _dropDownSubCategory.asStateFlow()

    private val _uiState = MutableStateFlow<AddCheckListUiState>(AddCheckListUiState.Idle)
    val uiState: StateFlow<AddCheckListUiState> = _uiState.asStateFlow()

    fun onCheckListNameChanged(newCheckListName: String){
        _checkListName.value = newCheckListName

        if (_uiState.value is AddCheckListUiState.Error){
            _uiState.value = AddCheckListUiState.Idle
        }
    }

    fun onCheckListDescriptionChanged(newCheckListDescription: String){
        _checkListDescription.value = newCheckListDescription

        if (_uiState.value is AddCheckListUiState.Error){
            _uiState.value = AddCheckListUiState.Idle
        }
    }

    fun onDropDownSubCategoryNewOptionChanged(newOptionSelected: String){
        _dropDownSubCategory.update { it.copy(optionSelected = newOptionSelected, expanded = false) }
    }

    fun onDropDownSubCategoryExpandedChanged(expanded: Boolean){
        _dropDownSubCategory.update { it.copy(expanded = expanded) }
    }

    fun addChecklist(onSuccess: () -> Unit){

        _uiState.value = AddCheckListUiState.Loading

        viewModelScope.launch {

            val addCheckListResult = addCheckListUseCase(checkListName.value,
                checkListDescription.value, dropDownSubCategory.value.optionSelected)

            when (addCheckListResult){

                is OperationResult.Success -> {
                    onSuccess()
                }

                is OperationResult.Error -> {
                    _uiState.value = AddCheckListUiState.Error("Erro ao criar uma CheckList: ${addCheckListResult.message}")
                }
            }

        }
    }
}






