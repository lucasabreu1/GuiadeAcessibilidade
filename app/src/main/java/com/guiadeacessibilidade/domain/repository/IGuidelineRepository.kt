package com.guiadeacessibilidade.domain.repository

import com.guiadeacessibilidade.domain.models.Guideline
import kotlinx.coroutines.flow.Flow

interface IGuidelineRepository {

    suspend fun getAll(): Flow<List<Guideline>>

    suspend fun loadGuidelineById(id: String): Guideline
}