package com.guiadeacessibilidade.ui

import com.guiadeacessibilidade.domain.models.Orientation
import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    object AuthGraph

    @Serializable
    object Login


    @Serializable
    object Register


    @Serializable
    object MainAppGraph

    @Serializable
    object CategoriesRoute

    @Serializable
    data class SubCategoriesRoute(
        val categoryId: String,
        val categoryName: String
    )


    @Serializable
    object CheckListRoute

    @Serializable
    data class OrientationsRoute(
        val subCategoryId: String,
        val subCategoryName: String
    )

    @Serializable
    data class OrientationItemRoute(
        val orientationId: String
    )

    @Serializable
    object AddCheckListRoute

    @Serializable
    data class ProgressCheckListRoute(
        val checkListId: String,
        val checkListSubCategoryId: String
    )

    @Serializable
    data class SuccessCriteriaRoute(
        val subCategoryId: String,
        val subCategoryName: String
    )

}