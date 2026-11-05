package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.Menu
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.models.User
import com.guiadeacessibilidade.domain.usacase.GetAllMenuUseCase
import com.guiadeacessibilidade.domain.usacase.GetCurrentUserUseCase
import com.guiadeacessibilidade.domain.usacase.LogoutUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface WelcomeUiState{
    object Loading: WelcomeUiState
    data class Success(val menuList: List<Menu>) : WelcomeUiState
    data class Error(val message: String): WelcomeUiState
}

sealed interface UserUiState {
    object Loading : UserUiState
    data class Success(val user: User) : UserUiState
    data class Error(val message: String) : UserUiState
}

class WelcomeViewModel(
    private val getAllMenuUseCase: GetAllMenuUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<WelcomeUiState>(WelcomeUiState.Loading)
    val uiState: StateFlow<WelcomeUiState> = _uiState.asStateFlow()

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

    fun loadMenus(){

        _uiState.value = WelcomeUiState.Loading

        viewModelScope.launch {
            getAllMenuUseCase().collect { menus ->

                when (menus){
                    is OperationResult.Success -> {
                        _uiState.value = WelcomeUiState.Success(menus.data)
                    }

                    is OperationResult.Error -> {
                        _uiState.value = WelcomeUiState.Error("Ocorreu um Erro.")
                    }
                }
            }
        }
    }
}












