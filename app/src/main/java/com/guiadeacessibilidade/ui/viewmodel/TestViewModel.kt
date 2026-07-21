package com.guiadeacessibilidade.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    val user = User(name= "Lucas", address = "Botafogo")
}

class User(
    val name: String,
    val address: String
)

class CounterState{
    var count by mutableStateOf(0)
        private set

    fun increment(){
        count++
    }
}