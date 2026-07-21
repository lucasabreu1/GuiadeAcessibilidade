package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.net.Network
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.ISubCategoryApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkSubCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseSubCategoryApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : ISubCategoryApi{
    override suspend fun getAll(): List<NetworkSubCategory> {

        var result = dbFirestore.collection("sub_category")
            .get()
            .addOnSuccessListener { result ->
                println("FIREBASE DATA -> SUCCESS !!!")
                for (document in result){
                    Log.d("TESTING", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TESTING", "Error getting document", exception)
            }.await()

        val list = withContext(dispatcher){
            val subCategoryList = result?.documents
                ?.mapNotNull { document ->

                    NetworkSubCategory(
                        document.id,
                        document.getString("category_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("svg_content") ?: ""
                    )
            }
            subCategoryList
        }
        return list ?: emptyList()
    }

    override suspend fun getSubCategoriesByCategoryId(categoryId: String): List<NetworkSubCategory> {

        var result = dbFirestore.collection("sub_category")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val list = withContext(dispatcher){

            val subCategoryList = result?.documents
                ?.filterNotNull()
                ?.filter { document ->
                    val _categoryId = document.getString("category_id") ?: ""
                    _categoryId == categoryId
                }
                ?.map { document ->

                    NetworkSubCategory(
                        document.id,
                        document.getString("category_id") ?: "",
                        document.getString("name") ?: "",
                        document.getString("svg_content") ?: ""
                    )
                }
            subCategoryList
        }
        return list ?: emptyList()
    }

    override suspend fun loadSubCategoryById(id: String): NetworkSubCategory {

        val result = dbFirestore.collection("sub_category")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val subCategoryDocument = withContext(dispatcher){
            result.documents.firstOrNull() { subCategory ->
                subCategory.id.equals(id)
            }
        }

        return NetworkSubCategory(
            subCategoryDocument?.id ?: "",
            subCategoryDocument?.getString("category_id") ?: "",
            subCategoryDocument?.getString("name") ?: "",
            subCategoryDocument?.getString("sub_category") ?: ""
        )

    }
}