package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.MenuEntity

interface IMenuDAOContract {

    suspend fun getAll(): List<MenuEntity>
}