package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.LoginUseCase
import com.guiadeacessibilidade.ui.viewmodel.LoginViewModel

class LoginViewModelFactory(
    private val getUserLoginUseCase: LoginUseCase
) : ViewModelProvider.Factory {

    override fun<T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(getUserLoginUseCase) as T
        }
        throw IllegalArgumentException("Unknow ViewModel class")
    }

}