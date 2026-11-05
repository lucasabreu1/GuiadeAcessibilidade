package com.guiadeacessibilidade.data.datasource.remote.firebase

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.guiadeacessibilidade.data.datasource.remote.api.ICategoryApi
import com.guiadeacessibilidade.data.datasource.remote.models.NetworkCategory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FirebaseCategoryApiImp(
    private val dbFirestore: FirebaseFirestore,
    private val dispatcher: CoroutineDispatcher

) : ICategoryApi{
    override suspend fun getAll(): List<NetworkCategory> {

        var result = dbFirestore.collection("category")
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
            val categoryList = result.documents.mapNotNull { document ->
                try
                {
                    NetworkCategory(
                        document.id,
                        document.getString("name") ?: "",
                        svgContent = document.getString("svg_content") ?: ""
                    )


                } catch (e: Exception)
                {
                    null
                }
            }
            categoryList
        }
        return list

    }

    override suspend fun loadCategoryById(id: String): NetworkCategory {

        val result = dbFirestore.collection("category")
            .get()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
            .await()

        val categoryDocument = withContext(dispatcher){
            result.documents.firstOrNull() { category ->
                category.id.equals(id)
            }
        }

        return NetworkCategory(
            categoryDocument?.id ?: "",
            categoryDocument?.getString("name") ?: "",
            svgContent = categoryDocument?.getString("name") ?: ""
        )

    }
}