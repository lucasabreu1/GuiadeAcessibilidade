package com.guiadeacessibilidade

import android.app.Application
import com.guiadeacessibilidade.di.AppContainer

class GuiaDeAcessibilidadeApplication : Application() {

    val appContainer: AppContainer by lazy {
        AppContainer(this)
    }
}