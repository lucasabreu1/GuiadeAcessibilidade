package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkGuideline

interface IGuidelineApi {

    suspend fun getAll(): List<NetworkGuideline>

    suspend fun loadGuidelineById(id: String): NetworkGuideline
}