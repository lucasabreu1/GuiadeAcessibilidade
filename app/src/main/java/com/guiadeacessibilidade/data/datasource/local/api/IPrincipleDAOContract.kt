package com.guiadeacessibilidade.data.datasource.local.api

import com.guiadeacessibilidade.data.datasource.local.entities.PrincipleEntity

interface IPrincipleDAOContract {

    suspend fun loadPrincipleById(id: String): PrincipleEntity

}