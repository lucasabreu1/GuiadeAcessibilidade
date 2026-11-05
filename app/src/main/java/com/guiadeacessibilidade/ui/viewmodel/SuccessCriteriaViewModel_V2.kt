package com.guiadeacessibilidade.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.CompleteSuccessCriteria
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.GetAllSuccessCriteriaUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class SuccessCriteriaUiState(
    val id: String,
    val title: String,
    val level: String,
    val link: String,
    val principle: String,
    val description: String
)

class SuccessCriteriaViewModel_V2(
    private val getAllSuccessCriteriaUseCase: GetAllSuccessCriteriaUseCase
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()
    private val _successCriteriaList = MutableStateFlow<List<CompleteSuccessCriteria>>(emptyList())
    val successCriteriaList: StateFlow<List<CompleteSuccessCriteria>> = _successCriteriaList.asStateFlow()

    private val _filteredSuccessCriteriaList = MutableStateFlow<List<CompleteSuccessCriteria>>(emptyList())
    val filteredSuccessCriteriaList: StateFlow<List<CompleteSuccessCriteria>>  = _filteredSuccessCriteriaList.asStateFlow()

    init {
        viewModelScope.launch {
            getAllSuccessCriteriaUseCase().collect {  successCriterias ->

                when(successCriterias){
                    is OperationResult.Success -> {

                        _successCriteriaList.value = successCriterias.data

                        if (searchText.value.isBlank()){
                            _filteredSuccessCriteriaList.value = successCriterias.data
                        }
                        else{
                            withContext(Dispatchers.IO){
                                _filteredSuccessCriteriaList.value =
                                    successCriterias.data.filter { item ->
                                        item.sucessCriteria.name.contains(searchText.value)
                                    }
                                }
                            }
                    }
                    is OperationResult.Error -> {
                        Log.d("FirebaseTAG", "ERROR!!")
                    }

                }
            }
        }
    }

    fun onQueryChanged(searchText: String){
        _searchText.value = searchText

        viewModelScope.launch {
            withContext(Dispatchers.Default)
            {
                if (_searchText.value.isBlank()){
                    _filteredSuccessCriteriaList.value = _successCriteriaList.value
                }
                if (!_searchText.value.isBlank()){
                    _filteredSuccessCriteriaList.value =
                        _successCriteriaList.value.filter { item ->
                            item.sucessCriteria.name.contains(_searchText.value)
                        }
                }
            }
        }
    }
}