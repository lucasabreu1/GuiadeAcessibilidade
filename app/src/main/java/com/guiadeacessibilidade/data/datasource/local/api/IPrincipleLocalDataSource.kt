package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.PrincipleEntity

interface IPrincipleLocalDataSource {

    suspend fun loadGuidelineById(id: String): PrincipleEntity
}