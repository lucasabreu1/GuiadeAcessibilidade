package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.Category
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.usacase.GetAllCategoryUseCase
import com.guiadeacessibilidade.domain.usacase.GetCurrentUserUseCase
import com.guiadeacessibilidade.domain.usacase.LogoutUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface CategoryUiState {
    object Loading: CategoryUiState
    data class Success(val categories: List<Category>) : CategoryUiState
    data class Error(val message: String) : CategoryUiState
}

//sealed interface UserUiState {
//    object Loading : UserUiState
//    data class Success(val user: User) : UserUiState
//    data class Error(val message: String) : UserUiState
//}

class CategoryViewModel(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow<CategoryUiState>(CategoryUiState.Loading)
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private val _userUiState = MutableStateFlow<UserUiState>(UserUiState.Loading)
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()


    fun getCurrentUser(){

        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()

            when(currentUser){
                is OperationResult.Success -> {
                    _userUiState.value = UserUiState.Success(currentUser.data)
                }
                is OperationResult.Error ->{
                    _userUiState.value = UserUiState.Error(currentUser.message)
                }
            }
        }
    }

    fun logoutCurrentUser(onLogoutSuccess: () -> Unit){

        viewModelScope.launch {
            logoutUserUseCase()
            onLogoutSuccess()
        }
    }

    fun loadCategories(){

        _uiState.value = CategoryUiState.Loading

        viewModelScope.launch {
            getAllCategoryUseCase().collect { categories ->

                when (categories){
                    is OperationResult.Success -> {
                        _uiState.value = CategoryUiState.Success(categories.data)
                    }

                    is OperationResult.Error -> {
                        _uiState.value = CategoryUiState.Error("Ocorreu um Erro.")
                    }
                }
            }
        }
    }
}