package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.MenuEntity

interface IMenuLocalDataSource {

    suspend fun getAll(): List<MenuEntity>
}