package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed interface LoginUiState{
    object Idle: LoginUiState
    data object Loading : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(
    private val userLoginUseCase: LoginUseCase
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail

        if (_uiState.value is LoginUiState.Error){
            _uiState.value = LoginUiState.Idle
        }
    }

    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword

        if (_uiState.value is LoginUiState.Error){
            _uiState.value = LoginUiState.Idle
        }
    }

    fun login(onSuccess: () -> Unit){

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {

            val userLogin = userLoginUseCase(email.value, password.value)

            when (userLogin) {

                is OperationResult.Success -> {
                    onSuccess()
                }

                is OperationResult.Error -> {
                    _uiState.value = LoginUiState.Error(userLogin.message)
                }
            }
        }

    }

}

















