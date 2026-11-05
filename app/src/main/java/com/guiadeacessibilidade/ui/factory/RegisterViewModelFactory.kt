package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.RegisterUserUseCase
import com.guiadeacessibilidade.ui.viewmodel.LoginViewModel
import com.guiadeacessibilidade.ui.viewmodel.RegisterViewModel

class RegisterViewModelFactory (
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            return RegisterViewModel(registerUserUseCase) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}