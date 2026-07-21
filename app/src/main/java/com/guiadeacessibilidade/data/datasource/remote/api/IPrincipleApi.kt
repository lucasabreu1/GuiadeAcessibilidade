package com.guiadeacessibilidade.data.datasource.remote.api

import com.guiadeacessibilidade.data.datasource.remote.models.NetworkPrinciple

interface IPrincipleApi {

    suspend fun loadPrincipleById(id: String): NetworkPrinciple
}