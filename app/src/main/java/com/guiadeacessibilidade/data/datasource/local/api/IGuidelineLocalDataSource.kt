package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.GuidelineEntity

interface IGuidelineLocalDataSource {

    suspend fun getAll(): List<GuidelineEntity>

    suspend fun loadGuidelineById(id: String): GuidelineEntity
}