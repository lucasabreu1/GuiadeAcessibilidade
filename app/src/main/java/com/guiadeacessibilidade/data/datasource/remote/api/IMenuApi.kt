package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkMenu

interface IMenuApi {

    suspend fun getAll(): List<NetworkMenu>
}