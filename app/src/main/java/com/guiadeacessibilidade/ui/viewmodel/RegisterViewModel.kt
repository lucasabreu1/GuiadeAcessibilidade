package com.guiadeacessibilidade.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guiadeacessibilidade.domain.models.OperationResult
import com.guiadeacessibilidade.domain.usacase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RegisterUiState{

    object Idle: RegisterUiState
    data object Loading: RegisterUiState
    data class Error(val message: String) : RegisterUiState
}


class RegisterViewModel (
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel(){

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChanged(newName: String){
        _name.value = newName

        if (_uiState.value is RegisterUiState.Error){
            _uiState.value = RegisterUiState.Idle
        }
    }


    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail

        if (_uiState.value is RegisterUiState.Error){
            _uiState.value = RegisterUiState.Idle
        }
    }

    fun onPasswordChanged(newPassword: String){
        _password.value = newPassword

        if (_uiState.value is RegisterUiState.Error){
            _uiState.value = RegisterUiState.Idle
        }
    }

    fun onRegisterClicked(onSuccess: () -> Unit){

        _uiState.value = RegisterUiState.Loading

        viewModelScope.launch {

            val userFirebase = registerUserUseCase(name.value, email.value, password.value)

            when (userFirebase){

                is OperationResult.Success -> {
                    onSuccess()
                }

                is OperationResult.Error -> {
                    _uiState.value = RegisterUiState.Error("Erro ao cadastrar usuario: ${userFirebase.message}")
                }
            }
        }
    }

}




