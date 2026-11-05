package com.guiadeacessibilidade.ui.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.guiadeacessibilidade.domain.usacase.GetAllMenuUseCase
import com.guiadeacessibilidade.domain.usacase.GetCurrentUserUseCase
import com.guiadeacessibilidade.domain.usacase.LogoutUserUseCase
import com.guiadeacessibilidade.ui.viewmodel.WelcomeViewModel

class WelcomeViewModelFactory (
    private val getAllMenuUseCase: GetAllMenuUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WelcomeViewModel::class.java)) {
            return WelcomeViewModel(getAllMenuUseCase, getCurrentUserUseCase, logoutUserUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}