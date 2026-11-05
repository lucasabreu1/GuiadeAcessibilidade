package com.guiadeacessibilidade.data.datasource.local.room

import androidx.room.Dao
import androidx.room.Query

import com.guiadeacessibilidade.data.datasource.local.entities.SuccessCriteriaEntity

@Dao
interface SuccessCriteriaDAO {

    @Query("SELECT * FROM success_criteria")
    fun getAll(): List<SuccessCriteriaEntity>

    @Query("SELECT * FROM success_criteria")
    fun getSuccessCriteriasBySubCategoryId(subCategoryId: String): List<SuccessCriteriaEntity>

    @Query("SELECT * FROM success_criteria")
    fun loadSuccessCriteriaById(id: String): SuccessCriteriaEntity
}